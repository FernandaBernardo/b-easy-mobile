package br.com.b_easy.DataBase;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.b_easy.DAO.SubjectDao;
import br.com.b_easy.DAO.TaskDao;
import br.com.b_easy.DAO.UserDao;
import br.com.b_easy.DAO.UserSubjectDao;
import br.com.b_easy.DataBaseModel.SubjectBD;
import br.com.b_easy.DataBaseModel.TaskBD;
import br.com.b_easy.DataBaseModel.UserBD;
import br.com.b_easy.DataBaseModel.UserSubjectBD;
import br.com.b_easy.Util;

/**
 * Created by Tiago on 9/24/2015.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "BeasyBD.sqlite";

    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database,ConnectionSource connectionSource) {
        try {
            Log.d("DataBase", "OnCreateX");

            TableUtils.createTable(connectionSource, UserBD.class);
            TableUtils.createTable(connectionSource, SubjectBD.class);
            TableUtils.createTable(connectionSource, UserSubjectBD.class);
            TableUtils.createTable(connectionSource, TaskBD.class);

//            SubjectDao subjectDao = new SubjectDao(connectionSource);
//            UserDao userDao = new UserDao(connectionSource);
//            UserSubjectDao userSubjectDao = new UserSubjectDao(connectionSource);
//            TaskDao taskDao = new TaskDao(connectionSource);
//
//            UserBD user = new UserBD("Tiago","tiago.missao@usp.br","123",21,"USP","SI",8);
//            UserBD user2 = new UserBD("Tiago Missao","t.missao@gmail.com","456",21,"EACH","SI",8);
//            userDao.create(user);
//            userDao.create(user2);
//
//
//            for(int i=1;i<=5; i++){
//
//                List<TaskBD> tasks = new ArrayList<>();
//
//                SubjectBD subject = new SubjectBD("Subject #" + i);
//
//                for(int j=1; j<=3 ; j++){
//                    tasks.add(new TaskBD("Task #" + j, "DOING !", "", Util.Task_Enum.DOING.toString(),new Date(),subject));
//                    tasks.add(new TaskBD("Task #" + j, "DONE !", "", Util.Task_Enum.DONE.toString(),new Date(),subject));
//                    tasks.add(new TaskBD("Task #" + j, "DO !", "", Util.Task_Enum.DO_TO.toString(),new Date(),subject));
//                }
//
//                subjectDao.create(subject);
//                userSubjectDao.create(new UserSubjectBD(user,subject));
//
//                for(TaskBD aux : tasks){
//                    taskDao.create(aux);
//                }
//
//            }
//
//            for(int i=1;i<=3; i++){
//
//                List<TaskBD> tasks = new ArrayList<>();
//                SubjectBD subject = new SubjectBD("Subject #0" + i);
//
//                for(int j=1; j<=3 ; j++){
//                    tasks.add(new TaskBD("Task #" + j, "DOING !", "", Util.Task_Enum.DOING.toString(),new Date(),subject));
//                    tasks.add(new TaskBD("Task #" + j, "DONE !", "", Util.Task_Enum.DONE.toString(),new Date(),subject));
//                    tasks.add(new TaskBD("Task #" + j, "DO !", "", Util.Task_Enum.DO_TO.toString(),new Date(),subject));
//                }
//
//                subjectDao.create(subject);
//                userSubjectDao.create(new UserSubjectBD(user2,subject));
//
//                for(TaskBD aux : tasks){
//                    taskDao.create(aux);
//                }
//
//
//            }


        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db,ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, UserBD.class, true);
            TableUtils.dropTable(connectionSource, SubjectBD.class, true);
            TableUtils.dropTable(connectionSource, UserSubjectBD.class, true);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "exception during onUpgrade", e);
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }

}
