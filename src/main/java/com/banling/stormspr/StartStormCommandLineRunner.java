package com.banling.stormspr;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.banling.stormspr.numcount.LocalStorm;

@Component
public class StartStormCommandLineRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		LocalStorm.startStorm();
	}

}
