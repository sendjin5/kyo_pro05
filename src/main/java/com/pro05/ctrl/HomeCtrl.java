package com.pro05.ctrl;

import com.pro05.entity.ProductVO;
import com.pro05.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeCtrl {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String home(Model model) {
        List<ProductVO> popularProducts = productService.popularProducts();
        System.out.println("인기 상품 : " + popularProducts);

        model.addAttribute("popularProducts", popularProducts);
        return "index";
    }

}
