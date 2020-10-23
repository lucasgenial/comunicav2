package br.com.cicom.comunicacicom.config;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryImpl;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jmx.export.annotation.AnnotationMBeanExporter;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.google.common.base.Preconditions;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactoryArquivoDs",
					   transactionManagerRef = "transactionManagerArquivoDs",
        			   basePackages = { "br.com.cicom.comunicacicom.DSArquivo" },
        			   repositoryBaseClass = DataTablesRepositoryImpl.class
)
public class ArquivoRepositoryConfig {

	private static final String PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE = "hibernate.jdbc.batch_size";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_NAME_HIBERNATE_FMT_SQL = "hibernate.format_sql";
	private static final String[] ENTITYMANAGER_PACKAGES_TO_SCAN = { "br.com.cicom.comunicacicom.DSArquivo" };

	private static final String DB_URL = "arquivo.datasource.url";
	private static final String DB_USER = "arquivo.datasource.username";
	private static final String DB_PASSWORD = "arquivo.datasource.password";
	private static final String DB_DRIVER = "arquivo.datasource.driver";
	private static final String DB_DIALECT = "arquivo.datasource.dialect";

	@Autowired
    private Environment env;
    
	@Bean
	public AnnotationMBeanExporter annotationMBeanExporter() {
		AnnotationMBeanExporter annotationMBeanExporter = new AnnotationMBeanExporter();
		annotationMBeanExporter.addExcludedBean("arquivoDS");
		annotationMBeanExporter.setRegistrationPolicy(RegistrationPolicy.IGNORE_EXISTING);
		return annotationMBeanExporter;
	}

	@Bean(destroyMethod = "close")
    public DataSource arquivoDS() {
        DataSource ds =  DataSourceBuilder.create()
        		.url(Preconditions.checkNotNull(env.getProperty(DB_URL)))
        		.username(Preconditions.checkNotNull(env.getProperty(DB_USER)))
        		.password(Preconditions.checkNotNull(env.getProperty(DB_PASSWORD)))
        		.driverClassName(Preconditions.checkNotNull(env.getProperty(DB_DRIVER)))
        		.build();
        return ds;
    }
	
	@Bean(name = "entityManagerFactoryArquivoDs")
    public EntityManagerFactory entityManagerFactoryArquivoDs() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setJpaVendorAdapter(vendorAdaptor());
		emf.setDataSource(arquivoDS());
		emf.setPersistenceUnitName("entityManagerFactoryArquivoDs");
		emf.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
        emf.setJpaProperties(jpaHibernateProperties());
		emf.afterPropertiesSet();
		return emf.getObject();		
	}

    @Bean(name = "entityManagerArquivoDs")
    public EntityManager entityManagerArquivoDs() {
        return entityManagerFactoryArquivoDs().createEntityManager();
    }	

	@Bean(name = "transactionManagerArquivoDs")
	public PlatformTransactionManager transactionManagerArquivoDs() {
		JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactoryArquivoDs());
        return tm;
	}
	
	private HibernateJpaVendorAdapter vendorAdaptor() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabasePlatform(env.getProperty(DB_DIALECT));
		vendorAdapter.setShowSql(false);
		return vendorAdapter;
	}

	private Properties jpaHibernateProperties() {
		Properties properties = new Properties();
		properties.put(PROPERTY_NAME_HIBERNATE_FMT_SQL, env.getProperty(PROPERTY_NAME_HIBERNATE_FMT_SQL));
		properties.put(PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE, env.getProperty(PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE));
		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		return properties;
	}
	
}