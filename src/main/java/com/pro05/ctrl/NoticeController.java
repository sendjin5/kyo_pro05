package com.pro05.ctrl;


import com.pro05.entity.Member;
import com.pro05.entity.Notice;
import com.pro05.service.MemberService;
import com.pro05.service.NoticeSerivce;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/notice/*")
public class NoticeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NoticeSerivce noticeSerivce;

    @Autowired
    private MemberService memberService;

    @Autowired
    private HttpSession session;


    @GetMapping("/List")
    public String NoticeList(Model model) {
        List<Notice> noticeList = noticeSerivce.boardList();
        model.addAttribute("noticeList", noticeList);
        return "/board/notice/noticeList";
    }

    @GetMapping("/Get")
    public String Noticeget(Model model, @RequestParam int no, HttpServletRequest request) throws Exception {

        Notice notice = noticeSerivce.boardGet(no);
        String par = notice.getTitle();

        model.addAttribute("notice", notice);
        return "/board/notice/noticeGet";
    }

    ;

    @GetMapping("/Edit")
    public String NoticeEditform(@RequestParam int no, Model model) {
        Notice notice = noticeSerivce.boardGet(no);
        model.addAttribute("notice", notice);
        return "/board/notice/noticeEdit";
    }


    @PostMapping("/Edit")
    public String NoticeEdit(MultipartFile uploadFiles, HttpServletRequest request, Model model) throws Exception {
        int no = Integer.parseInt(request.getParameter("no"));
        Notice notice = noticeSerivce.boardGet(no);
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String img = request.getParameter("img");
        notice.setTitle(title);
        notice.setContent(content);

        if (uploadFiles != null) {

            Resource resource = new ClassPathResource("/static/images");
            String uploadDir = resource.getFile().getAbsolutePath();

            // 업로드된 파일 이름 중복 방지를 위해 유니크한 파일 이름 생성
            String fileName = StringUtils.cleanPath(uploadFiles.getOriginalFilename());
            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;

            // 업로드 디렉토리 경로 생성
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 파일을 업로드 디렉토리로 저장
            Path filePath = uploadPath.resolve(uniqueFileName);
            File dest = filePath.toFile();
            uploadFiles.transferTo(dest);
            notice.setImg(uniqueFileName);
//
//            ServletContext application = request.getSession().getServletContext();
//            String realPath = application.getRealPath("resources/static/images");          // 운영 서버 저장폴더
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//            Date date = new Date();
//            String dateFolder = sdf.format(date);
//            String originalThumbnailname = uploadFiles.getOriginalFilename();
//            UUID uuid = UUID.randomUUID();
//
//            File folder = new File(realPath);
//            if (!folder.exists()) {        // 폴더가 존재하지 않으면 폴더 생성
//                folder.mkdirs();
//            }
//            String uploadThumbnailname = uuid.toString() + "_" + originalThumbnailname;
//            uploadFiles.transferTo(new File(realPath, uploadThumbnailname));     //파일 등록
//            notice.setImg(uploadThumbnailname);
        } else {
            notice.setImg(img);
        }

        noticeSerivce.boardEdit(notice);
        return "redirect:/notice/List";
    }

    @GetMapping("/Add")
    public String Noticeform(Model model) {
        String id = (String) session.getAttribute("sid");
        Member mem = memberService.memberGet(id);
        model.addAttribute("mem", mem);
        return "/board/notice/noticeForm";
    }


    @PostMapping("/Add")
    public String NoticeAdd(Notice notice, MultipartFile uploadFiles, HttpServletRequest request, Model model) throws Exception {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        notice.setTitle(title);
        notice.setContent(content);

        if (uploadFiles != null) {

            Resource resource = new ClassPathResource("/static/images");
            String uploadDir = resource.getFile().getAbsolutePath();

            // 업로드된 파일 이름 중복 방지를 위해 유니크한 파일 이름 생성
            String fileName = StringUtils.cleanPath(uploadFiles.getOriginalFilename());
            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;

            // 업로드 디렉토리 경로 생성
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 파일을 업로드 디렉토리로 저장
            Path filePath = uploadPath.resolve(uniqueFileName);
            File dest = filePath.toFile();
            uploadFiles.transferTo(dest);
            notice.setImg(uniqueFileName);

//
//            ServletContext application = request.getSession().getServletContext();
//            String realPath = application.getRealPath("resources/static/images");          // 운영 서버 저장폴더
////            String realPath = "";                                              //application.yml location 적용시 폴더
//
//            logger.info("ㅡㅡㅡㅡㅡㅡㅡ"+ realPath);
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//            Date date = new Date();
//            String dateFolder = sdf.format(date);
//            String originalThumbnailname = uploadFiles.getOriginalFilename();
//            UUID uuid = UUID.randomUUID();
//            String uploadThumbnailname = uuid.toString() + "_" + originalThumbnailname;
//
//            File folder = new File(realPath);
//            if (!folder.exists()) {        // 폴더가 존재하지 않으면 폴더 생성
//                folder.mkdirs();
//            }
//            uploadFiles.transferTo(new File(realPath, uploadThumbnailname));     //파일 등록
//            notice.setImg(uploadThumbnailname);
//            logger.info("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ11" + realPath);
        }

        noticeSerivce.boardAdd(notice);
        return "redirect:/notice/List";
    }

    @GetMapping("/Del")
    public String NoticeDel(int no) {
        noticeSerivce.boardDel(no);
        return "redirect:/notice/List";
    }



}
