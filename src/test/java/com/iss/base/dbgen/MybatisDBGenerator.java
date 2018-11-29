package com.iss.base.dbgen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.PluginConfiguration;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.google.common.base.CaseFormat;

import freemarker.template.TemplateExceptionHandler;
import static com.iss.base.dbgen.DBGenConsts.*;
public class MybatisDBGenerator {
	// JDBC configuration
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/sb-base";
	private static final String JDBC_USERNAME = "root";
	private static final String JDBC_PASSWORD = "1234";
	private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

	private static final String PROJECT_PATH = System.getProperty("user.dir");// disk path
	private static final String TEMPLATE_FILE_PATH = PROJECT_PATH + "/src/test/resources/mybatisDBGen/template";// template
																												// path

	private static final String JAVA_PATH = "/src/main/java";
	private static final String RESOURCES_PATH = "/src/main/resources";

	private static final String PACKAGE_PATH_SERVICE = packageConvertPath(SERVICE_PACKAGE);
	private static final String PACKAGE_PATH_SERVICE_IMPL = packageConvertPath(SERVICE_IMPL_PACKAGE);
	private static final String PACKAGE_PATH_CONTROLLER = packageConvertPath(CONTROLLER_PACKAGE);

	private static final String AUTHOR = "Junjie Sun";// @author
	private static final String DATE = new SimpleDateFormat("yyyy/MM/dd").format(new Date());// @date

	public static void main(String[] args) {
		// genCode("t_user");
		genCodeByCustomModelName("role", "Role");
	}

	/**
	 * Generate code through the data table name, the Model name is obtained by
	 * parsing the data table name, and the underlining turns the form of the hump.
	 * example "t_user_detail" will generate
	 * TUserDetail、TUserDetailMapper、TUserDetailService ...
	 * 
	 * @param tableNames
	 */
	public static void genCode(String... tableNames) {
		for (String tableName : tableNames) {
			genCodeByCustomModelName(tableName, null);
		}
	}

	/**
	 * Generating the code by the data table name, and the custom Model name
	 * generated code such as the input table name "t_user_detail" and the custom
	 * Model name "User" will be generated User、UserMapper、UserService ...
	 * 
	 * @param tableName
	 * @param modelName
	 */
	public static void genCodeByCustomModelName(String tableName, String modelName) {
		genModelAndMapper(tableName, modelName);
		genService(tableName, modelName);
		genController(tableName, modelName);
	}

	public static void genModelAndMapper(String tableName, String modelName) {
		Context context = new Context(ModelType.FLAT);
		context.setId("Potato");
		context.setTargetRuntime("MyBatis3Simple");
		context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
		context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");

		JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
		jdbcConnectionConfiguration.setConnectionURL(JDBC_URL);
		jdbcConnectionConfiguration.setUserId(JDBC_USERNAME);
		jdbcConnectionConfiguration.setPassword(JDBC_PASSWORD);
		jdbcConnectionConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
		context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

		PluginConfiguration pluginConfiguration = new PluginConfiguration();
		pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
		pluginConfiguration.addProperty("mappers", MAPPER_INTERFACE_REFERENCE);
		context.addPluginConfiguration(pluginConfiguration);

		JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
		javaModelGeneratorConfiguration.setTargetProject(PROJECT_PATH + JAVA_PATH);
		javaModelGeneratorConfiguration.setTargetPackage(MODEL_PACKAGE);
		context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

		SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
		sqlMapGeneratorConfiguration.setTargetProject(PROJECT_PATH + RESOURCES_PATH);
		sqlMapGeneratorConfiguration.setTargetPackage("mapping");
		context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

		JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
		javaClientGeneratorConfiguration.setTargetProject(PROJECT_PATH + JAVA_PATH);
		javaClientGeneratorConfiguration.setTargetPackage(MAPPER_PACKAGE);
		javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
		context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

		TableConfiguration tableConfiguration = new TableConfiguration(context);
		tableConfiguration.setTableName(tableName);
		if (StringUtils.isNotEmpty(modelName))
			tableConfiguration.setDomainObjectName(modelName);
		tableConfiguration.setGeneratedKey(new GeneratedKey("id", "Mysql", true, null));
		context.addTableConfiguration(tableConfiguration);

		List<String> warnings;
		MyBatisGenerator generator;
		try {
			Configuration config = new Configuration();
			config.addContext(context);
			config.validate();

			boolean overwrite = true;
			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			warnings = new ArrayList<String>();
			generator = new MyBatisGenerator(config, callback, warnings);
			generator.generate(null);
		} catch (Exception e) {
			throw new RuntimeException("Model and Mapper generate failure.", e);
		}

		if (generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {
			throw new RuntimeException("Model and Mapper generate failure：" + warnings);
		}
		if (StringUtils.isEmpty(modelName))
			modelName = tableNameConvertUpperCamel(tableName);
		System.out.println(modelName + ".java generate successful.");
		System.out.println(modelName + "Mapper.java generate successful.");
		System.out.println(modelName + "Mapper.xml generate successful.");
	}

	public static void genService(String tableName, String modelName) {
		try {
			freemarker.template.Configuration cfg = getConfiguration();

			Map<String, Object> data = new HashMap<>();
			data.put("date", DATE);
			data.put("author", AUTHOR);
			String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName)
					: modelName;
			data.put("modelNameUpperCamel", modelNameUpperCamel);
			data.put("modelNameLowerCamel", tableNameConvertLowerCamel(tableName));
			data.put("basePackage", BASE_PACKAGE);

			File file = new File(
					PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE + modelNameUpperCamel + "Service.java");
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			cfg.getTemplate("service.ftl").process(data, new FileWriter(file));
			System.out.println(modelNameUpperCamel + "Service.java generate successful.");

			File file1 = new File(
					PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE_IMPL + modelNameUpperCamel + "ServiceImpl.java");
			if (!file1.getParentFile().exists()) {
				file1.getParentFile().mkdirs();
			}
			cfg.getTemplate("service-impl.ftl").process(data, new FileWriter(file1));
			System.out.println(modelNameUpperCamel + "ServiceImpl.java generate successful.");
		} catch (Exception e) {
			throw new RuntimeException("Service generate failure.", e);
		}
	}

	public static void genController(String tableName, String modelName) {
		try {
			freemarker.template.Configuration cfg = getConfiguration();

			Map<String, Object> data = new HashMap<>();
			data.put("date", DATE);
			data.put("author", AUTHOR);
			String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName)
					: modelName;
			data.put("baseRequestMapping", modelNameConvertMappingPath(modelNameUpperCamel));
			data.put("modelNameUpperCamel", modelNameUpperCamel);
			data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
			data.put("basePackage", BASE_PACKAGE);

			File file = new File(
					PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CONTROLLER + modelNameUpperCamel + "Controller.java");
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			// cfg.getTemplate("controller-restful.ftl").process(data, new
			// FileWriter(file));
			cfg.getTemplate("controller-swagger.ftl").process(data, new FileWriter(file));

			System.out.println(modelNameUpperCamel + "Controller.java generate successful.");
		} catch (Exception e) {
			throw new RuntimeException("Controller generate failure.", e);
		}

	}

	private static freemarker.template.Configuration getConfiguration() throws IOException {
		freemarker.template.Configuration cfg = new freemarker.template.Configuration(
				freemarker.template.Configuration.VERSION_2_3_23);
		cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FILE_PATH));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		return cfg;
	}

	private static String tableNameConvertLowerCamel(String tableName) {
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName.toLowerCase());
	}

	private static String tableNameConvertUpperCamel(String tableName) {
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());

	}

	private static String tableNameConvertMappingPath(String tableName) {
		tableName = tableName.toLowerCase();// Use a capital table name compatible
		return "/" + (tableName.contains("_") ? tableName.replaceAll("_", "/") : tableName);
	}

	private static String modelNameConvertMappingPath(String modelName) {
		String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, modelName);
		return tableNameConvertMappingPath(tableName);
	}

	private static String packageConvertPath(String packageName) {
		return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
	}

}
