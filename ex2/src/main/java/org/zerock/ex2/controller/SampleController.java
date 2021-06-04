package org.zerock.ex2.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.ex2.dto.SampleDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Log4j2
@RequestMapping("/sample")
public class SampleController {

    @GetMapping({"/ex2", "/exLink"})
    public void exModel1(Model model1) {
        List<SampleDTO> list = IntStream.rangeClosed(1, 20).asLongStream().mapToObj(i -> {
            SampleDTO dto = SampleDTO.builder()
                    .sno(i)
                    .first("first..." + i)
                    .last("last ... " + i)
                    .regTime(LocalDateTime.now())
                    .build();
            return dto;
        }).collect(Collectors.toList());
        model1.addAttribute("list", list);

    }

    @GetMapping("/exInline")
    public String exInline(RedirectAttributes redirectAttributes) {
        log.info("ex Inline .........");

        SampleDTO dto = SampleDTO.builder()
                .sno(100L)
                .first("first...100")
                .last("last..100")
                .regTime(LocalDateTime.now())
                .build();
        redirectAttributes.addFlashAttribute("result", "success");
        redirectAttributes.addFlashAttribute("dto", dto);

        return "redirect:/sample/ex3";

    }

    @GetMapping("/ex3")
    public void ex3(){

        log.info("ex3");

    }





}
