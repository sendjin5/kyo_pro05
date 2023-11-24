package com.pro05.ctrl;

import com.pro05.entity.Category;
import com.pro05.entity.Member;
import com.pro05.entity.Notice;
import com.pro05.entity.ProductVO;
import com.pro05.service.MemberService;
import com.pro05.service.NoticeSerivce;
import com.pro05.service.ProductService;
import com.pro05.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/**")
public class AdminCtrl {
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;
    @Autowired
    private NoticeSerivce noticeSerivce;
    @Autowired
    private HttpSession session;

    @GetMapping("home")
    public String adminhome(Model model){
        List<Member> memberList = memberService.memberCreateStats();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        List<String> createLabel = memberList.stream()
                .map(member -> formatter.format(member.getCreateAt()))
                .collect(Collectors.toList());

        model.addAttribute("createLabel", createLabel);

        List<Long> createData = memberList.stream().map(Member::getMno).collect(Collectors.toList());
        model.addAttribute("createData", createData);

        return "admin/home";
    }

    @GetMapping("memberList")
    public String memberList(HttpServletRequest request, Model model){
        Page page = new Page();

        String searchType = request.getParameter("type");
        String searchKeyword = request.getParameter("keyword");
        int pageNow = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        page.setSearchType(searchType);
        page.setSearchKeyword(searchKeyword);
        page.setPageNow(pageNow);

        model.addAttribute("type", searchType);
        model.addAttribute("keyword", searchKeyword);

        page.setPostTotal(memberService.memberCount(page));
        page.makePage();

        model.addAttribute("page", page);

        List<Member> memberList = memberService.memberList(page);
        model.addAttribute("memberList", memberList);

        return "admin/memberList";
    }

    @GetMapping("productList")
    public String productList(HttpServletRequest request, Model model){

        int curPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        String type = request.getParameter("type");
        String keyword = request.getParameter("keyword");
        String category = request.getParameter("cate");

        Page page = new Page();
        page.setPageNow(curPage);
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

        return "admin/productList";
    }

    @GetMapping("noticeList")
    public String noticeList(HttpServletRequest request, Model model){
        List<Notice> noticeList = noticeSerivce.boardList();
        model.addAttribute("noticeList", noticeList);

        return "admin/noticeList";
    }

    @GetMapping("/noticeInsert")
    public String Noticeform(Model model) {
        String id = (String) session.getAttribute("sid");
        Member mem = memberService.memberGet(id);
        model.addAttribute("mem", mem);
        return "/admin/noticeInsert";
    }

    @PostMapping("/noticeInsert")
    public String NoticeAdd(Notice notice, MultipartFile uploadFiles, HttpServletRequest request, Model model) throws Exception {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        notice.setTitle(title);
        notice.setContent(content);

        if (uploadFiles != null) {
//            ServletContext application = request.getSession().getServletContext();
//            String realPath = application.getRealPath("classpath/static/");          // 운영 서버 저장폴더
            String realPath = "";                                              //application.yml location 적용시 폴더

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            String dateFolder = sdf.format(date);
            String originalThumbnailname = uploadFiles.getOriginalFilename();
            UUID uuid = UUID.randomUUID();
            String uploadThumbnailname = uuid.toString() + "_" + originalThumbnailname;
            uploadFiles.transferTo(new File(realPath, uploadThumbnailname));     //파일 등록
            notice.setImg(uploadThumbnailname);
        }
        noticeSerivce.boardAdd(notice);

        return "redirect:noticeList";
    }

    @GetMapping("/noticeDelete")
    public String NoticeDel(int no) {
        noticeSerivce.boardDel(no);
        return "redirect:noticeList";
    }


    @GetMapping("/memberGet")
    public String memberGet(String id, Model model){
        Member member = memberService.memberGet(id);
        model.addAttribute("member", member);
        return "/admin/memberGet";
    }

}
