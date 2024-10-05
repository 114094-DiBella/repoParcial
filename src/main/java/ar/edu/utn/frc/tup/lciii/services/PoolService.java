package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.dtos.common.PoolDto;

import java.util.List;

public interface PoolService {

    PoolDto getPool(String pool);
    List<PoolDto> getAllPools(); // <3>


}
