package com.vertineko.shospital.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Data
public class GatewayConfiguration {

    private List<String> whitePathList;
}
