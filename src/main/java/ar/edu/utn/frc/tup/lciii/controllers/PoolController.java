package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.common.PoolDto;
import ar.edu.utn.frc.tup.lciii.services.PoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PoolController {

    @Autowired
    private PoolService poolService;

    @GetMapping("/pools")
    public ResponseEntity<List<PoolDto>> getPools() {

        return ResponseEntity.ok(poolService.getAllPools());
    }
}
