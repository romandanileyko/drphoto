package ru.danileyko.controller;

import com.sun.tracing.dtrace.ModuleAttributes;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import ru.danileyko.model.Photo;
import ru.danileyko.model.User;
import ru.danileyko.service.PhotoService;
import ru.danileyko.service.SecurityService;
import ru.danileyko.service.UserService;
import ru.danileyko.service.UserServiceImpl;
import ru.danileyko.validator.FileValidator;
import ru.danileyko.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danil on 11.02.2017.
 */
@Controller
public class IndexController {

    static Log log = LogFactory.getLog(IndexController.class.getName());
    // Вывод основной страницы.
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private FileValidator fileValidator;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PhotoService photoService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String showIndexPage(ModelMap modelMap)
    {
        log.info("Show index page.");
        modelMap.addAttribute("user",getPrincipal());
        List<Photo> allPhotos = new ArrayList<Photo>();
        List<String> base64StringAll = new ArrayList<String>();
        allPhotos = photoService.getAllPhoto();
        modelMap.addAttribute("allPhotos",allPhotos);
        for (Photo photo : allPhotos)
        {
            try{
                byte[] bytes64 = Base64.encode(photo.getPhoto());
                base64StringAll.add(new String(bytes64 , "UTF-8"));
                log.info("Get photo with name : "+photo.getName());
            }catch (IOException e){
                System.out.print(e.toString());
            }
        }
        modelMap.addAttribute("allPhoto",base64StringAll);
        return "index";
    }
    //Переход на страницу админ.

    @RequestMapping(value = "/admin",method = RequestMethod.GET)
    public String adminPage(ModelMap modelMap)
    {
        log.info("Show admin page. User is "+ getPrincipal());
        modelMap.addAttribute("user",getPrincipal());
        return "admin";
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String getRegisterPage(Model model)
    {
        log.info("Show register page ");
        model.addAttribute("userForm",new User());
        return "register";
    }
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String registerPage(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model)
    {
        userValidator.validate(userForm,bindingResult);

        if(bindingResult.hasErrors())
            return "register";

        userService.save(userForm);
        securityService.autologin(userForm.getUsername(),userForm.getPassword());
        return "redirect:/";
    }
    //Обработка LogOut'а.

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null)
        {
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/load",method = RequestMethod.GET)
    public String loadPage(Model model)
    {
        List<Photo> photos = new ArrayList<Photo>();
        List<String> base64String = new ArrayList<String>();
        model.addAttribute("fileUpload",new Photo());
        log.info("Logged user is"+getPrincipal());
        log.info("User is"+userService.getUser(getPrincipal()));
        photos = photoService.getPhoto(userService.getUser(getPrincipal()));
        model.addAttribute("photo",photos);
        if(photos!=null)
        {
            for (Photo photo:photos)
            {
                try{
                    byte[] bytes64 = Base64.encode(photo.getPhoto());
                    base64String.add(new String(bytes64 , "UTF-8"));
                }catch (IOException e){
                    System.out.print(e.toString());
                }
                log.info("Get photo with name : "+photo.getName());
            }
        }
         model.addAttribute("img",base64String);
        return "load";
    }
    @RequestMapping(value = "/load",method = RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String loadPage(@ModelAttribute("fileUpload") Photo photo,
                           @RequestParam("file") MultipartFile file,
                           BindingResult bindingResult)
    {
        fileValidator.validate(file,bindingResult);
        if(bindingResult.hasErrors())
        {
            return "load";
        }
        User user = userService.getUser(getPrincipal());
        String title = photo.getName();
        int ownerId = user.getId();
        try
        {
            byte[] photoBinary = file.getBytes();
           // byte[] photoBinary = photo.getPhoto();
            log.info("Title is " + title);
            log.info("File is "  + photoBinary);
            log.info("Owner is " + ownerId);
            photo.setName(title);
            photo.setPhoto(photoBinary);
            photo.setUser_id(user);
            photoService.save(photo);
            log.info("Photo is saved.");
        } catch (Exception e)
        {
            log.info(e.toString());
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/delete")
    public @ResponseBody void deletePhotiById(@ModelAttribute("photo") Photo photo,
                                  @RequestParam("photoId") String photoId){
        Integer id = Integer.parseInt(photoId);
        photoService.deletePhotoById(id);
        log.info("Photo with id= "+id+" was deleted.");
    }

    //Получение имени пользователя.

    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}

