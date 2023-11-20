package com.chunjae.pro05.ctrl;


import com.chunjae.pro05.biz.BoardSerivce;
import com.chunjae.pro05.biz.FilesService;
import com.chunjae.pro05.entity.Board;
import com.chunjae.pro05.entity.Files;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/board/*")
public class boardController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BoardSerivce boardSerivce;

    @Autowired
    private FilesService filesService;


    @GetMapping("/List")
    public String boardList(Model model) {
        List<Board> boardList = boardSerivce.boardList();
        model.addAttribute("boardList", boardList);
        return "/board/boardList";
    }

    @GetMapping("/Get")
    public String boardget(Model model, @RequestParam int no) throws Exception{

        Board board = boardSerivce.boardGet(no);
        String par = board.getTitle();
        Files files = filesService.fileListByPar(par);

        model.addAttribute("board", board);
        model.addAttribute("files", files);
        return "board/boardGet";
    };

    @GetMapping("/Add")
    public String boardform(){
        return "/board/boardForm";
    };

    @PostMapping("/Add")
    public String boardAdd(Board board, List<MultipartFile> uploadFiles, HttpServletRequest request, Model model) throws Exception {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        board.setTitle(title);
        board.setContent(content);

        boardSerivce.boardAdd(board);
        model.addAttribute("board", board);


        logger.info("board--------------" + board);
        logger.info("uploadFiles--------------" + uploadFiles);


        if (uploadFiles != null) {
//            ServletContext application = request.getSession().getServletContext();
            String realPath = "D:\\kyo\\pro\\pro05\\src\\main\\resources\\upload/";       // 개발 서버
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            String dateFolder = sdf.format(date);

            logger.info("realPath--------------" + realPath);


            File uploadPath = new File(realPath, dateFolder);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }
            logger.info("uploadPath--------------" + uploadPath);

            for (MultipartFile multipartFile : uploadFiles) {
                if (multipartFile.isEmpty()) {
                    continue;
                }

                logger.info("multipartFile--------------" + multipartFile);



                String originalFilename = multipartFile.getOriginalFilename();
                UUID uuid = UUID.randomUUID();
                String uploadFilename = uuid.toString() + "_" + originalFilename;
                logger.info("originalFilename--------------" + originalFilename);
                logger.info("uploadFilename--------------" + uploadFilename);

                Files files = new Files();
                files.setPar(title);
                files.setSaveFolder(dateFolder);

                String fileType = multipartFile.getContentType();
                String[] fileTypeArr = fileType.split("/");
                files.setFileType(fileTypeArr[0]);

                files.setOriginNm(originalFilename);
                files.setSaveNm(uploadFilename);

                multipartFile.transferTo(new File(uploadPath, uploadFilename));     // 서버에 파일 업로드 수행
                filesService.filesInsert(files);                                  // DB 등록
                logger.info("files--------------" + files);

            }

        }

        return "redirect:/board/List";
    };

    @GetMapping("/Del")
    public String boardDel(int no){
        boardSerivce.boardDel(no);
        return "redirect:/board/List";
    };

}
