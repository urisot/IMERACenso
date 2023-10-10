package com.imera.censo.Services;

import com.imera.censo.Contracts.Models.OPR_USUARIOS;

public class PostPadron {
    private String pass;
    private PostCenso censo;

    public PostPadron(String pass, PostCenso censo) {
        this.pass = pass;
        this.censo = censo;
    }
}
