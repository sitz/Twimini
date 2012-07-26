package com.directi.train.tweetapp.controllers;

import com.directi.train.tweetapp.model.TweetItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: elricl
 * Date: 23/7/12
 * Time: 1:24 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class GenericController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
