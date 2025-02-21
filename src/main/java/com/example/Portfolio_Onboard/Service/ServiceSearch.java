package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.Entity.EntityPost;
import com.example.Portfolio_Onboard.Entity.EntityWorld;

import java.util.List;

public interface ServiceSearch {

    List<EntityWorld> setSearchResultBoard(String keyword);
    List<EntityPost> setSearchResultPost(String keyword);
}
