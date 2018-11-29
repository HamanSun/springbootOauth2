package com.iss.base.controller;

import java.security.Principal;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iss.base.annotation.SysLogger;
import com.iss.base.common.Result;
import com.iss.base.common.ResultGenerator;
import com.iss.base.model.User;
import com.iss.base.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
* Created by Junjie Sun on 2018/11/19.
*/
@Api(tags="UserApiDoc")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping
    @ApiOperation("New 1 User")
    @ApiImplicitParam(name="user",value="user information", dataType="User", paramType="body")
    public Result add(@RequestBody User user) {
        userService.save(user);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete 1 User")
    @ApiImplicitParam(name="id",value="user primary key", dataType="Long", paramType="path")
    public Result delete(@PathVariable Long id) {
    	log.info("Will delete user with primary key : " + id);
        userService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
 	@ApiOperation("Update 1 User")
    @ApiImplicitParam(name="user",value="user with primary key", dataType="User", paramType="body")
    public Result update(@RequestBody User user) {
        userService.update(user);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    @SysLogger("根据ID查询")
    @ApiOperation("Get User detail")
    @ApiImplicitParam(name="id",value="user primary key", dataType="Integer", paramType="path")
    public Result detail(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResultGenerator.genSuccessResult(user);
    }
    
    @GetMapping
    public Principal user(Principal user){
        return user;
    }

    /*@GetMapping
    @ApiOperation("List all Users")
    @ApiImplicitParams({@ApiImplicitParam(name="page",value="page number", dataType="Integer", paramType="form"),@ApiImplicitParam(name="size",value="page size", dataType="Integer", paramType="form")})
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<User> list = userService.findAll();
        PageInfo<User> pageInfo = new PageInfo<User>(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }*/
}
