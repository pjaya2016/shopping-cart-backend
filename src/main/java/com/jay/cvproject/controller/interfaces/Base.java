package com.jay.cvproject.controller.interfaces;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1")
public interface Base {
}
