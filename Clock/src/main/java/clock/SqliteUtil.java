package clock;

import clock.vo.PriceVo;
import clock.vo.RegionVo;
import clock.vo.SkuPriceVo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteUtil {

    public static void init() throws Exception{
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS region(region STRING,area STRING,regioncode STRING)");
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS cost(sku STRING,price STRING,datestr STRING,mark STRING)");
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS price(itemname STRING,itemprice INTEGER,regioncode STRING,itemnum INTEGER)");
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS item(itemname STRING)");
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS timetemp(nowstr STRING)");
        stmt.close();
        conn.close();
    }
    public void insertRegion(List<RegionVo> regionVos)throws Exception{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        regionVos.forEach(i->{
            try {
                stmt.executeUpdate("INSERT INTO region VALUES('"+i.getRegion()+"', '"+i.getArea()+"','"+i.getRegionCode()+"')");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        stmt.close();
        conn.close();
    }

    public void insertCost(List<SkuPriceVo> skuPriceVo)throws Exception{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        skuPriceVo.forEach(i->{
            try {
                stmt.executeUpdate("INSERT INTO cost VALUES('"+i.getSku()+"', '"+i.getPrice()+"','"+i.getDateStr()+"','"+i.getMark()+"')");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        stmt.close();
        conn.close();
    }
    public void insertItemEnum(String item)throws Exception{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        try {
            stmt.executeUpdate("INSERT INTO item VALUES('"+item+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        stmt.close();
        conn.close();
    }
    public void insertPrice(PriceVo priceVo)throws Exception{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
            try {
                stmt.executeUpdate("INSERT INTO price VALUES('"+priceVo.getItemName()+"', '"+priceVo.getItemPrice()+"','"+priceVo.getRegionCode()+"','"+priceVo.getItemNum()+"')");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        stmt.close();
        conn.close();
    }
    public List<String> queryAllRegion()throws Exception{
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
    public List<String> queryAllItem()throws Exception{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT itemname FROM item");
        List<String> itemList = new ArrayList<>();
        while(rs.next()){
            itemList.add(rs.getString("itemname"));
        }
        stmt.close();
        conn.close();
        return itemList;
    }
    public List<SkuPriceVo> queryAllCost()throws Exception{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT sku,price,datestr,mark FROM cost ");
        List<SkuPriceVo> skuPriceVoList = new ArrayList<>();
        while(rs.next()){
            SkuPriceVo skuPriceVo = new SkuPriceVo();
            skuPriceVo.setSku(rs.getString("sku"));
            skuPriceVo.setPrice(rs.getString("price"));
            skuPriceVo.setDateStr(rs.getString("datestr"));
            skuPriceVo.setMark(rs.getString("mark"));
            skuPriceVoList.add(skuPriceVo);
        }
        stmt.close();
        conn.close();
        return skuPriceVoList;
    }
    public List<String> queryAllSkc(String mark)throws Exception{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT sku FROM cost WHERE mark='"+mark+"'");
        List<String> itemList = new ArrayList<>();
        while(rs.next()){
            itemList.add(rs.getString("sku"));
        }
        stmt.close();
        conn.close();
        return itemList;
    }
    public void removeSku(String sku,String mark)throws Exception{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        boolean rs = stmt.execute("DELETE FROM cost WHERE sku='"+sku+"' AND mark='"+mark+"'");
        stmt.close();
        conn.close();
    }
    public List<SkuPriceVo> queryCostBySku(String sku)throws Exception{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT sku,price,datestr,mark FROM cost  where sku like '%"+sku+"%'");
        List<SkuPriceVo> skuPriceVoList = new ArrayList<>();
        while(rs.next()){
            SkuPriceVo skuPriceVo = new SkuPriceVo();
            skuPriceVo.setSku(rs.getString("sku"));
            skuPriceVo.setPrice(rs.getString("price"));
            skuPriceVo.setDateStr(rs.getString("datestr"));
            skuPriceVo.setMark(rs.getString("mark"));
            skuPriceVoList.add(skuPriceVo);
        }
        stmt.close();
        conn.close();
        return skuPriceVoList;
    }
    public List<SkuPriceVo> queryCostByMark(String mark)throws Exception{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT sku,price,datestr,mark FROM cost  where mark = '"+mark+"'");
        List<SkuPriceVo> skuPriceVoList = new ArrayList<>();
        while(rs.next()){
            SkuPriceVo skuPriceVo = new SkuPriceVo();
            skuPriceVo.setSku(rs.getString("sku"));
            skuPriceVo.setPrice(rs.getString("price"));
            skuPriceVo.setDateStr(rs.getString("datestr"));
            skuPriceVo.setMark(rs.getString("mark"));
            skuPriceVoList.add(skuPriceVo);
        }
        stmt.close();
        conn.close();
        return skuPriceVoList;
    }


    public List<RegionVo> queryAreaByRegion(String region)throws Exception{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT region,area,regioncode FROM region where region='"+region+"'");
        List<RegionVo> regionList = new ArrayList<>();
        while(rs.next()){
            RegionVo regionVo = new RegionVo();
            regionVo.setRegion(rs.getString("region"));
            regionVo.setArea(rs.getString("area"));
            regionVo.setRegionCode(rs.getString("regioncode"));
            regionList.add(regionVo);
        }
        stmt.close();
        conn.close();
        return regionList;
    }

    public List<PriceVo> queryPriceByRegion(String regionCode)throws Exception{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT itemname,itemprice,regioncode,itemnum FROM price where regioncode='"+regionCode+"' ORDER BY itemname");
        List<PriceVo> regionList = new ArrayList<>();
        while(rs.next()){
            PriceVo regionVo = new PriceVo();
            regionVo.setItemName(rs.getString("itemname"));
            regionVo.setItemPrice(rs.getInt("itemprice"));
            regionVo.setRegionCode(rs.getString("regioncode"));
            regionVo.setItemNum(rs.getInt("itemnum"));
            regionList.add(regionVo);
        }
        stmt.close();
        conn.close();
        return regionList;
    }
}
