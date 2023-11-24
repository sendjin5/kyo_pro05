package com.pro05.ctrl;

import com.pro05.entity.Keyword;
import com.pro05.entity.Member;
import com.pro05.entity.ProductVO;
import com.pro05.entity.WishProduct;
import com.pro05.service.KeywordService;
import com.pro05.service.MemberService;
import com.pro05.service.ProductService;
import com.pro05.service.WishService;
import com.pro05.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/myshop/**")
public class MyShopCtrl {
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WishService wishService;
    @Autowired
    private KeywordService keywordService;
    @Autowired
    private HttpSession session;

    @GetMapping("/")
    public String myShop() {
        return "redirect:myshop/products";
    }

    @GetMapping("products")
    public String myProducts(HttpServletRequest request, Model model) {
        String sid = (String) session.getAttribute("sid");
        Member member = memberService.memberGet(sid);
        model.addAttribute("member", member);

        // 페이징 처리
        Page page = new Page();
        String searchType = request.getParameter("type");
        String searchKeyword = request.getParameter("keyword");
        int pageNow = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        page.setSearchType(searchType);
        page.setSearchKeyword(searchKeyword);
        page.setPageNow(pageNow);
        //System.out.println(page.getPageNow());

        model.addAttribute("type", searchType);
        model.addAttribute("keyword", searchKeyword);
        //model.addAttribute("page", pageNow);
        // 추가해야됨
        page.setPostTotal(productService.productCountBySeller(sid, page));
        page.makePage();
        model.addAttribute("page", page);

        List<ProductVO> products = productService.productListBySeller(sid, page);
        //System.out.println("total:"+page.getPostTotal());
        model.addAttribute("products", products);

        return "/myshop/myProducts";
    }

    @GetMapping("wish")
    public String myWish(HttpServletRequest request, Model model) {
        String sid = (String) session.getAttribute("sid");
        Member member = memberService.memberGet(sid);
        model.addAttribute("member", member);

        // 페이징 처리
        Page page = new Page();
        String searchType = request.getParameter("type");
        String searchKeyword = request.getParameter("keyword");
        int pageNow = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        page.setSearchType(searchType);
        page.setSearchKeyword(searchKeyword);
        page.setPageNow(pageNow);
        //System.out.println(page.getPageNow());

        model.addAttribute("type", searchType);
        model.addAttribute("keyword", searchKeyword);
        page.setPostTotal(wishService.wishProductCount(sid, page));
        page.makePage();
        model.addAttribute("page", page);

        List<WishProduct> wishProductList = wishService.wishProductList(sid, page);
        System.out.println("찜 상품 : " + wishProductList);
        //System.out.println("total:" + page.getPostTotal());

        model.addAttribute("wishProductList", wishProductList);
        return "/myshop/myWish";
    }

    @GetMapping("keyword")
    public String myKeywords(HttpServletRequest request, Model model) {
        String sid = (String) session.getAttribute("sid");
        Member member = memberService.memberGet(sid);
        model.addAttribute("member", member);

        // 페이징 처리
        Page page = new Page();
        String searchType = request.getParameter("type");
        String searchKeyword = request.getParameter("keyword");
        int pageNow = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        page.setSearchType(searchType);
        page.setSearchKeyword(searchKeyword);
        page.setPageNow(pageNow);
        //System.out.println(page.getPageNow());

        model.addAttribute("type", searchType);
        model.addAttribute("keyword", searchKeyword);
        // 수정 필요
        page.setPostTotal(keywordService.keywordCountByUid(sid, page));
        page.makePage();
        model.addAttribute("page", page);

        List<Keyword> keywords = keywordService.keywordsByUid(sid, page);
        //System.out.println("키워드 목록 : " + keywords);
        model.addAttribute("keywords", keywords);
        return "/myshop/myKeywords";
    }

    @PostMapping("addWord")
    public String checkKeyword(HttpServletRequest request, Model model) {
        String uid = (String) session.getAttribute("sid");

        Keyword keyword = new Keyword();
        keyword.setWord(request.getParameter("word"));
        keyword.setUid(uid);
        System.out.println("keyword : " + keyword);
        keywordService.keywordInsert(keyword);
        return "redirect:keyword";
    }

    @GetMapping("wishRemove")
    public String removeWish(@RequestParam Long wno, Model model) {
        Long pno = wishService.wishGet(wno).getPno();
        wishService.wishRemove(wno);
        wishService.decreaseWish(pno);
        return "redirect:wish";
    }

    @GetMapping("keywordRemove")
    public String removeKeyword(@RequestParam Long kno, Model model) {
        //Long pno = wishService.wishGet(wno).getPno();
        //wishService.wishRemove(wno);
        //wishService.decreaseWish(pno);
        keywordService.keywordDelete(kno);
        return "redirect:keyword";
    }


}
