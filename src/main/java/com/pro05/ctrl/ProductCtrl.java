package com.pro05.ctrl;

import com.pro05.entity.*;
import com.pro05.service.ChatService;
import com.pro05.service.ProductService;
import com.pro05.service.WishService;
import com.pro05.util.Page;
import com.pro05.entity.Category;
import com.pro05.entity.FileData;
import com.pro05.entity.Product;
import com.pro05.entity.ProductVO;
import com.pro05.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/product/**")
public class ProductCtrl {
    @Autowired
    private ProductService productService;
    @Autowired
    private WishService wishService;
    @Autowired
    private ChatService chatService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    // 전체 상품 리스트
    @GetMapping("list")
    public String productList(HttpServletRequest request, Model model) throws Exception {

        int curPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        String type = request.getParameter("type");
        String keyword = request.getParameter("keyword");
        String category = request.getParameter("cate");

        Page page = new Page();
        page.setPageNow(curPage);                                           // 현재 페이지
        page.setCategory(category);                                        // 카테고리 데이터
        page.setSearchKeyword(request.getParameter("keyword"));     // 검색 키워드
        page.setSearchType(request.getParameter("type"));           // 검색 타입
        page.setProaddr(request.getParameter("proaddr"));
        page.setStatus(request.getParameter("status"));

        // 페이징에 필요한 데이터 저장
        int total = productService.getCount(page);
        page.setPostTotal(total);
        page.makePage();

        List<ProductVO> productList = productService.productList(page);
        List<Category> categories = productService.categories();

        // 로그인한 회원의 주소 정보 불러오기
        model.addAttribute("proaddr", request.getAttribute("addr3"));
        // 상품의 판매상태 불러오기
        String status = request.getParameter("status");

        model.addAttribute("status", status);
        model.addAttribute("productList", productList);
        model.addAttribute("categories", categories);
        model.addAttribute("curPage", curPage);
        model.addAttribute("curCategory", category);
        model.addAttribute("type", type);
        model.addAttribute("keyword", keyword);

        model.addAttribute("page", page);

        return "product/productList";
    }

    @GetMapping("detail")
    public String productDetial(@RequestParam Long pno, HttpServletRequest request, Model model) {
        ProductVO detail = productService.productDetail(pno);
        model.addAttribute("detail", detail);

        HttpSession session = request.getSession();
        //String sid = (String) session.getAttribute("sid");
        Object sidObeject = session.getAttribute("sid");
        String sid = sidObeject == null ? "" : (String) sidObeject;

        int flag = wishService.wishFind(pno, sid);
        model.addAttribute("flag", flag);
        //System.out.println("flag : " + flag);
        model.addAttribute("uid", sid);
        if(sid.equals(detail.getSeller())){
            List<ChatRoom> roomList = chatService.chatRoomProductList(pno);
            model.addAttribute("roomList", roomList);
        }
        return "product/productDetail";
    }

    @GetMapping("insert")
    public String productInsertForm(HttpServletRequest request, Model model, Product product) {
        HttpSession session = request.getSession();
        Object sidObeject = session.getAttribute("sid");
        //String sid = sidObeject == null ? "" : (String) sidObeject;
        if (sidObeject == null) {
            model.addAttribute("msg", "상품 판매는 로그인 후에 가능합니다.");
            model.addAttribute("url", "/member/login");
            return "/member/alert";
        }

        List<Category> categories = productService.categories();
        model.addAttribute("categories", categories);
        model.addAttribute("proaddr", session.getAttribute("proaddr"));
        return "product/productInsert";
    }

    @PostMapping("insert")
    public String productInsertPro(Product product, @RequestParam("upfile") MultipartFile[] files, HttpServletRequest request, Model model, RedirectAttributes rttr) throws IOException {
        HttpSession session = request.getSession();

        Resource resource = new ClassPathResource("/static/images");
        String uploadDir = resource.getFile().getAbsolutePath();


        String realPath = "C://upload/";
//        String realPath = "D:/kim/project/tproj/project05/pro05/src/main/resources/static/images";
        String today = new SimpleDateFormat("yyMMdd").format(new Date());
        String saveFolder = uploadDir + today;
        System.out.println(saveFolder);

        File folder = new File(saveFolder);
        if (!folder.exists()) {        // 폴더가 존재하지 않으면 폴더 생성
            folder.mkdirs();
        }

        List<FileData> fileDataList = new ArrayList<>();
//        3030cd5b-59af-4ce2-8ddb-907bdf2c4fcb_4.jpg
        for (MultipartFile file : files) {
            FileData fileData = new FileData();
            String originalFileName = file.getOriginalFilename();   // 첨부파일의 실제 이름

            // 업로드된 파일 이름 중복 방지를 위해 유니크한 파일 이름 생성
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;

            // 업로드 디렉토리 경로 생성
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 파일을 업로드 디렉토리로 저장
            Path filePath = uploadPath.resolve(uniqueFileName);

            if (!file.isEmpty()) { // 파일이 비어있지 않은 경우에만 처리
                File dest = filePath.toFile();

                String saveFilename = UUID.randomUUID().toString() + "_" + originalFileName;
                fileData.setTableName("product");
                fileData.setColumnNo(product.getPno());
                fileData.setOriginName(originalFileName);
//                fileData.setSaveName(saveFilename);
                fileData.setSaveName(uniqueFileName);
                fileData.setSavePath(today);
                fileData.setFileType(" ");
                fileData.setStatus("ACTIVE");
                file.transferTo(dest); // 파일을 업로드 폴더에 저장
//                file.transferTo(new File(saveFolder, saveFilename); // 파일을 업로드 폴더에 저장
            }
            fileDataList.add(fileData);

        }

        product.setFileDataList(fileDataList);
        String sid = (String) session.getAttribute("sid");
        product.setSeller(sid);

        productService.productInsert(product);
        return "redirect:list";
    }

    @GetMapping("edit")
    public String productEditForm(HttpServletRequest request, @RequestParam("pno") Long pno, Model model) {
        ProductVO product = productService.productDetail(pno);
        List<Category> categories = productService.categories();
        int imgCount = product.getFileDataList().size();

        model.addAttribute("detail", product);
        model.addAttribute("cateList", categories);
        model.addAttribute("imgCount", imgCount);

        return "product/productEdit";
    }

    @PostMapping("edit")
    public String productEditPro(Product product, @RequestParam("upfile") MultipartFile[] files, HttpServletRequest request, Model model, RedirectAttributes rttr) throws IOException {
        HttpSession session = request.getSession();

        String realPath = "C://upload/";
//        String realPath = "D:/kim/project/tproj/project05/pro05/src/main/resources/static/images";
        String today = new SimpleDateFormat("yyMMdd").format(new Date());
        String saveFolder = realPath + today;
        System.out.println(saveFolder);

        File folder = new File(saveFolder);
        if (!folder.exists()) {        // 폴더가 존재하지 않으면 폴더 생성
            folder.mkdirs();
        }

        List<FileData> fileDataList = new ArrayList<>();

        for (MultipartFile file : files) {
            FileData fileData = new FileData();
            String originalFileName = file.getOriginalFilename();   // 첨부파일의 실제 이름

            if (!file.isEmpty()) { // 파일이 비어있지 않은 경우에만 처리

                String saveFilename = UUID.randomUUID().toString() + "_" + originalFileName;
                fileData.setTableName("product");
                fileData.setColumnNo(product.getPno());
                fileData.setOriginName(originalFileName);
                fileData.setSaveName(saveFilename);
                fileData.setSavePath(today);
                fileData.setFileType(" ");
                fileData.setStatus("ACTIVE");
                file.transferTo(new File(saveFolder, saveFilename)); // 파일을 업로드 폴더에 저장
                fileDataList.add(fileData);
            }
        }

        product.setFileDataList(fileDataList);
        String sid = (String) session.getAttribute("sid");
        product.setSeller(sid);

        productService.productUpdate(product);
        return "redirect:list";
    }

    @GetMapping("imageDelete")
    @ResponseBody
    public int imageDelete (@RequestParam("fileNo") Long fileNo) {
        return productService.fileDataDelete(fileNo);
    }

    @GetMapping("delete")
    public String productDelete (@RequestParam("pno") Long pno) {
        productService.productRemove(pno);
        return "redirect:list";
    }

    @PostMapping("wish")
    @ResponseBody
    public Map<String, Integer> wishProduct(@ModelAttribute Wish wish) {
        int result = wishService.checkWish(wish);
        System.out.println("result : " + result);

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("res", result);
        resultMap.put("heartCnt", wishService.wishCount(wish.getPno()));
        return resultMap;
    }
}
