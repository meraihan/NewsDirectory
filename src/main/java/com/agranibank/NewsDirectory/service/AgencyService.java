package com.agranibank.NewsDirectory.service;


import com.agranibank.NewsDirectory.dao.AgencyDao;
import com.agranibank.NewsDirectory.model.Agency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Sayed Mahmud Raihan on 12/06/18.
 */

@Service
public class AgencyService {

    @Autowired
    AgencyDao agencyDao;

    public boolean addAgency(Agency agency) {
        if(agencyDao.add(agency).getId() > 0){
            return true;
        }
        return false;
    }

    public List<Agency> findAllAgency() {
        List<Agency> agencyList = agencyDao.findAll();
        return agencyList;
    }

    public boolean delete(Integer agencyId) {
        return agencyDao.delete(agencyId);
    }

    public Agency findById(Integer agencyId) {
        return agencyDao.findById(agencyId);
    }

    public boolean update(Agency agency) {
        agency.setUpdatedAt(new Date());
        return agencyDao.update(agency);
    }
}
