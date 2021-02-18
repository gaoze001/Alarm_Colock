package clock;

import clock.vo.PriceList;
import clock.vo.RegionList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteUtil {

    public static void init() throws Exception{
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS region(region STRING,area STRING,regioncode STRING)");
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS price(itemname STRING,itemprice INTEGER,regioncode STRING)");
        stmt.close();
        conn.close();
    }
    public void insertRegion(List<RegionList> regionLists)throws Exception{
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        regionLists.forEach(i->{
            try {
                stmt.executeUpdate("INSERT INTO region VALUES('"+i.getRegion()+"', '"+i.getArea()+"','"+i.getRegionCode()+"')");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        stmt.close();
        conn.close();
    }
    public void insertPrice(List<PriceList> PriceList)throws Exception{
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        PriceList.forEach(i->{
            try {
                stmt.executeUpdate("INSERT INTO price VALUES('"+i.getItemName()+"', '"+i.getItemPrice()+"','"+i.getRegionCode()+"')");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        stmt.close();
        conn.close();
    }
    public List<String> queryAllRegion()throws Exception{
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT distinct(region) region FROM region");
        List<String> regionList = new ArrayList<>();
        while(rs.next()){
            regionList.add(rs.getString("region"));
        }
        stmt.close();
        conn.close();
        return regionList;
    }
    public List<RegionList> queryAreaByRegion(String region)throws Exception{
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT region,area,regioncode FROM region where region='"+region+"'");
        List<RegionList> regionList = new ArrayList<>();
        while(rs.next()){
            RegionList regionVo = new RegionList();
            regionVo.setRegion(rs.getString("region"));
            regionVo.setArea(rs.getString("area"));
            regionVo.setRegionCode(rs.getString("regioncode"));
            regionList.add(regionVo);
        }
        stmt.close();
        conn.close();
        return regionList;
    }

    public List<PriceList> queryPriceByRegion(String regionCode)throws Exception{
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT itemname,itemprice,regioncode FROM price where regioncode='"+regionCode+"'");
        List<PriceList> regionList = new ArrayList<>();
        while(rs.next()){
            PriceList regionVo = new PriceList();
            regionVo.setItemName(rs.getString("itemname"));
            regionVo.setItemPrice(rs.getInt("itemprice"));
            regionVo.setRegionCode(rs.getString("regioncode"));
            regionList.add(regionVo);
        }
        stmt.close();
        conn.close();
        return regionList;
    }
}
