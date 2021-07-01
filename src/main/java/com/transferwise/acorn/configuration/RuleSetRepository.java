package com.transferwise.acorn.configuration;

import com.transferwise.acorn.models.Rule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(RuleSetRepository.RuleSet.class)
public class RuleSetRepository {

	@ConfigurationProperties(prefix = "wise")
	@Data
	@Validated
	@Builder
	@ConstructorBinding
	public static class RuleSet {
		private Map<String, Rule> ruleset;
	}

}
