package com.infodev.sanimaotp.controller.UIController;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Value("${dpmLocationAndroid}")
    private String dpmLocationAndroid;

    @Value("${dpmLocationIos}")
    private String dpmLocationIos;

    @Value("${applicationUrl}")
    private String appUrl;

    @RequestMapping({"/", "/logout", "/login"})
    public String indexPage() {
        return "index";
    }

    @GetMapping("/downloadDPM_")
    public String getDPM(Model model) {
        model.addAttribute("androidApp", appUrl + "/" + dpmLocationAndroid);
        model.addAttribute("iosApp", appUrl + "/" + dpmLocationIos);
        return "pages/downloadDPM";
    }


}
