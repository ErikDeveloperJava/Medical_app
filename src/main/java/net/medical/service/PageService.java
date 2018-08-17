package net.medical.service;

import net.medical.model.User;
import net.medical.page.DoctorPage;
import net.medical.page.MainPage;
import net.medical.page.PostPage;

public interface PageService {

    MainPage getMain();

    DoctorPage getDoctor(User user, int doctorId);

    PostPage getPost(int id);
}
