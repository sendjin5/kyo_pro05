package com.chunjae.pro05.ctrl;


import com.chunjae.pro05.biz.BoardSerivce;
import com.chunjae.pro05.biz.FilesService;
import com.chunjae.pro05.biz.ProductSerivce;
import com.chunjae.pro05.entity.Board;
import com.chunjae.pro05.entity.Files;
import com.chunjae.pro05.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/product/*")
public class ProductController {

    @Autowired
    private ProductSerivce productSerivce;



    @GetMapping("/List")
    public String productList(Model model, String addr1) throws Exception{
            List<Product> productAllList = productSerivce.productAllList();
            List<Product> productSelList = productSerivce.productSelList(addr1);
            model.addAttribute("proAllList", productAllList);
            model.addAttribute("proSelList", productSelList);
        return "/product/productList";
    }

    @GetMapping("/Get")
    public String productget(Model model, @RequestParam int pno) throws Exception{
            Product product = productSerivce.productGet(pno);
            model.addAttribute("product", product);
        return "product/productGet";
    };

    @GetMapping("/Add")
    public String productform(){
        return "/product/productForm";
    };

    @GetMapping("/TemAdd")
    public String productTemList(Model model){
        List<Product> proList = productSerivce.productTemList();
        model.addAttribute("proList", proList);
        return "/product/productTemList";
    };

    @PostMapping("/Add")
    public String productAdd(Product product, MultipartFile thumbnail, HttpServletRequest request, Model model) throws Exception {

            ServletContext application = request.getSession().getServletContext();
            String realPath = application.getRealPath("classpath:/static/images/");       // 운영 서버

                if(thumbnail != null) {
                    String originaluploadFiles = thumbnail.getOriginalFilename();
                    UUID uuid = UUID.randomUUID();
                    String uploaduploadFiles = uuid.toString() + "_" + originaluploadFiles;
                    thumbnail.transferTo(new File(realPath, uploaduploadFiles));     //파일 등록
                    product.setThumbnail(uploaduploadFiles);
                }
                productSerivce.productAdd(product);

        return "redirect:/product/List";
    };

    @PostMapping("/Edit")
    public String productEdit(Product product, MultipartFile thumbnail, HttpServletRequest request, Model model) throws Exception {

        ServletContext application = request.getSession().getServletContext();
        String realPath = application.getRealPath("classpath:/static/images/");       // 운영 서버

        if(thumbnail != null) {
            String originaluploadFiles = thumbnail.getOriginalFilename();
            UUID uuid = UUID.randomUUID();
            String uploaduploadFiles = uuid.toString() + "_" + originaluploadFiles;
            thumbnail.transferTo(new File(realPath, uploaduploadFiles));     //파일 등록
            product.setThumbnail(uploaduploadFiles);
        }
        productSerivce.productAdd(product);

        return "redirect:/product/List";
    };

    @GetMapping("/Del")
    public String productDel(int pno){
         productSerivce.productDel(pno);
        return "redirect:/product/List";
    };

}
