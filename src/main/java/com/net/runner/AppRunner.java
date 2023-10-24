package com.net.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.net.entity.EligibilityDetails;
import com.net.repo.EligibilityDetailsRepo;


@Component
public class AppRunner  implements ApplicationRunner{
	
	@Autowired
	public EligibilityDetailsRepo repo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		EligibilityDetails entity = new EligibilityDetails();
		entity.setEligId(1);
		entity.setName("Pooja");
		entity.setEmail("poojajaiswar@yahoo.com");
		entity.setMobile(8108090231L);
		entity.setGender('F');
		entity.setSsn(45555458L);
		//entity.setPlanStartDate(24-10-2024);
		entity.setPlanName("SNAP-CHAT");
		entity.setPlanStatus("Approved");
		
		repo.save(entity);
		
		EligibilityDetails entity1 = new EligibilityDetails();
		entity1.setEligId(2);
		entity1.setName("Jyoti");
		entity1.setEmail("jjazz.650@yahoo.com");
		entity1.setMobile(8419991388L);
		entity1.setGender('F');
		entity1.setSsn(856485524L);
		//entity.setPlanStartDate(24-10-2024);
		entity1.setPlanName("WhatsApp");
		entity1.setPlanStatus("Denied");
		repo.save(entity1);
		
		
	}

}
