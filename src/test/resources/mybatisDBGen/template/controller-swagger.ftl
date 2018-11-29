package ${basePackage}.controller;

import ${basePackage}.common.Result;
import ${basePackage}.common.ResultGenerator;
import ${basePackage}.model.${modelNameUpperCamel};
import ${basePackage}.service.${modelNameUpperCamel}Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by ${author} on ${date}.
*/
@Api(tags="${modelNameUpperCamel}ApiDoc")
@Slf4j
@RestController
@RequestMapping("${baseRequestMapping}")
public class ${modelNameUpperCamel}Controller {
    @Resource
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    @PostMapping
    @ApiOperation("New 1 ${modelNameUpperCamel}")
    @ApiImplicitParam(name="${modelNameLowerCamel}",value="${modelNameLowerCamel} information", dataType="${modelNameUpperCamel}", paramType="body")
    public Result add(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.save(${modelNameLowerCamel});
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete 1 ${modelNameUpperCamel}")
    @ApiImplicitParam(name="id",value="${modelNameLowerCamel} primary key", dataType="Long", paramType="path")
    public Result delete(@PathVariable Long id) {
    	log.info("Will delete ${modelNameLowerCamel} with primary key : " + id);
        ${modelNameLowerCamel}Service.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
 	@ApiOperation("Update 1 ${modelNameUpperCamel}")
    @ApiImplicitParam(name="${modelNameLowerCamel}",value="${modelNameLowerCamel} with primary key", dataType="${modelNameUpperCamel}", paramType="body")
    public Result update(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.update(${modelNameLowerCamel});
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    @ApiOperation("Get ${modelNameUpperCamel} detail")
    @ApiImplicitParam(name="id",value="${modelNameLowerCamel} primary key", dataType="Long", paramType="path")
    public Result detail(@PathVariable Long id) {
        ${modelNameUpperCamel} ${modelNameLowerCamel} = ${modelNameLowerCamel}Service.findById(id);
        return ResultGenerator.genSuccessResult(${modelNameLowerCamel});
    }

    @GetMapping
    @ApiOperation("List all ${modelNameUpperCamel}s")
    @ApiImplicitParams({@ApiImplicitParam(name="page",value="page number", dataType="Integer", paramType="form"),@ApiImplicitParam(name="size",value="page size", dataType="Integer", paramType="form")})
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Service.findAll();
        PageInfo<${modelNameUpperCamel}> pageInfo = new PageInfo<${modelNameUpperCamel}>(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
