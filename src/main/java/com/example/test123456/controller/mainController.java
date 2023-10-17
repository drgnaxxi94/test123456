package com.example.test123456.controller;

import com.example.test123456.dto.NotificationFormDto;
import com.example.test123456.service.BoardService;
import com.example.test123456.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class mainController {
    @Autowired
    MainService mainService;
    @Autowired
    BoardService boardService;
    @GetMapping(value = "/")
    public String gotoMain(Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 15);
        Page<NotificationFormDto> notices = mainService.getMainNoticePage(pageable);

        model.addAttribute("notices", notices);

        return "main";
    }

}
