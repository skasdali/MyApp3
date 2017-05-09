@GrabResolver(name='Spring Snapshot', root='http://repo.spring.io/snapshot')
@Grab('org.springframework.cloud:spring-service-connector:0.9.6.BUILD-SNAPSHOT')
@Grab('org.springframework.cloud:cloudfoundry-connector:0.9.6.BUILD-SNAPSHOT')


import java.util.Map;
import java.security.MessageDigest;
import org.springframework.cloud.Cloud
import org.springframework.cloud.CloudFactory

import javax.servlet.http.HttpServletRequest

import groovy.util.logging.Commons


beans {
	cloudFactory(CloudFactory)

	cloud(cloudFactory: "getCloud")
}

@RestController
@Configuration
@Commons
class WebApplication  implements CommandLineRunner {

	int requestsServed=1

	@Autowired
	Cloud cloud

	@RequestMapping("/")
	String home(Map<String,Object> model, HttpServletRequest request) {

		model['color'] = 'blue'
		model['instance'] = cloud.applicationInstanceInfo.properties['instance_index']
		model['requestsServed'] = requestsServed++
		String appName = cloud.applicationInstanceInfo.properties['application_name']
		model['appName'] = appName
		
		model['idx'] = getIdx(appName);
		template 'index.html', model
	}
	
	@Override
	void run(String... args) {
		println "Started..."
	}
	
	int getIdx(String appName)
	{
		long sum = 0
		for (byte b: appName.getBytes())
			sum += b
		return (int) (sum % 8)+1
	}
}
