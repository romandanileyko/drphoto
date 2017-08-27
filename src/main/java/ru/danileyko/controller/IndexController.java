package ru.danileyko.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
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
import ru.danileyko.aop.LoggingTimeExecution;
import ru.danileyko.model.Category;
import ru.danileyko.model.Comment;
import ru.danileyko.model.Photo;
import ru.danileyko.model.User;
import ru.danileyko.service.*;
import ru.danileyko.validator.FileValidator;
import ru.danileyko.validator.UserValidator;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CommentService commentService;

    @LoggingTimeExecution
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
        List<User> allUsers = new ArrayList<>();
        log.info("Show admin page. User is "+ getPrincipal());
        modelMap.addAttribute("user",getPrincipal());
        allUsers = userService.getAllUsers();
        modelMap.addAttribute("userlist",allUsers);
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
        List<Category> categoryList = new ArrayList<Category>();
        List<String> base64String = new ArrayList<String>();
        Map<Integer,String> map = new HashMap<>();
        model.addAttribute("fileUpload",new Photo());

        log.info("Logged user is"+getPrincipal());
        log.info("User is"+userService.getUser(getPrincipal()));
        photos = photoService.getPhoto(userService.getUser(getPrincipal()));
        categoryList = categoryService.getAllCategory();
        for (Category item: categoryList){
            map.put(item.getId(),item.getName());
        }
        model.addAttribute("category",categoryList);
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
        Category category = categoryService.getAllCategory().get(1);
        String title = photo.getName();
        Integer categoryId = photo.getCategory_id().getId();
        int ownerId = user.getId();
        try
        {
            byte[] photoBinary = file.getBytes();
            InputStream inputStream = new ByteArrayInputStream(photoBinary);
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            BufferedImage scaledImage = Scalr.resize(bufferedImage, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH,600,Scalr.OP_ANTIALIAS);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(scaledImage,"jpg",byteArrayOutputStream);
            byteArrayOutputStream.flush();
            byte[] scaledImgBytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            log.info("Title is " + title);
            log.info("File is "  + photoBinary);
            log.info("Owner is " + ownerId);
            log.info("Selected category is " + categoryId);
            photo.setName(title);
            photo.setPhoto(scaledImgBytes);
            photo.setUser_id(user);
            photo.setCategory_id(photo.getCategory_id());
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

    @RequestMapping(value = "/delete-user-{id}", method = RequestMethod.GET)
    public @ResponseBody String deleyeUser(@PathVariable String id) {
        Integer userId = Integer.parseInt(id);
        userService.delete(userId);
        log.info("User was deleted");
        return "User was deleted!";
    }
    @RequestMapping(value = "/disable-user-{id}",method = RequestMethod.GET)
    public @ResponseBody void userLock(@PathVariable String id){
        Integer userId = Integer.parseInt(id);
        userService.userLock(userId);
    }
    @RequestMapping(value = "/enable-user-{id}",method = RequestMethod.GET)
    public @ResponseBody void userUnLock(@PathVariable String id){
        Integer userId = Integer.parseInt(id);
        userService.userUnLock(userId);
    }

    @RequestMapping(value = "/one-photo-{id}",method = RequestMethod.GET)
    public String onePhoto(@PathVariable String id,ModelMap modelMap){
        int photoId = Integer.parseInt(id);
        Photo photo = photoService.getOnePhoto(photoId);
        List<Comment> commentList = commentService.getAllCommentByPhotoId(photo);
        modelMap.addAttribute("user",getPrincipal());
        modelMap.addAttribute("photoObject",photo);//for display name
        modelMap.addAttribute("commentList",commentList);
        byte[] bytes64 = Base64.encode(photo.getPhoto());
        try {
            String photoStr = new String(bytes64, "UTF-8");
            modelMap.addAttribute("onePhoto",photoStr);//for display image
        }catch (Exception e){System.out.print(e);}
        log.info("photoId: "+id);

        return "photo";
    }
    @RequestMapping(value = "/addComment",method = RequestMethod.POST)
    public @ResponseBody String addComent(@RequestParam("comment") String comment,
                                          @RequestParam("photoId") String id) {
        int photoId = Integer.parseInt(id);
        String photoOwner = getPrincipal();
        Comment comment1 = new Comment();
        comment1.setId_photo(photoService.getOnePhoto(photoId));
        comment1.setComment(comment);
        comment1.setId_user(userService.getUser(photoOwner));
        commentService.saveComment(comment1);
        log.info("photoId: "+photoId+"photoOwner: "+photoOwner+" comment: "+comment);
        log.info("Comment was saved.");
        return "comment was saved.";
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

