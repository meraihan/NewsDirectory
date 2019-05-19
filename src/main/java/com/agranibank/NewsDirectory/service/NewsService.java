package com.agranibank.NewsDirectory.service;

import com.agranibank.NewsDirectory.dao.NewsDao;
import com.agranibank.NewsDirectory.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Sayed Mahmud Raihan on 12/07/18.
 */

@Service
public class NewsService {

    @Autowired
    private NewsDao newsDao;

    public List<News> findAllNews() {
        List<News> newsList = newsDao.findAll();
        return newsList;
    }

    public boolean addAgency(News news) {
        if(newsDao.add(news).getId() > 0){
            return true;
        }
        return false;
    }

    public News findById(Integer newsId) {
        return newsDao.findById(newsId);
    }

    public boolean update(News news) {
        news.setUpdatedAt(new Date());
        return newsDao.update(news);
    }

    public boolean delete(Integer agencyId) {
        return newsDao.delete(agencyId);
    }

}