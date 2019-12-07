package com.ysxsoft.common_base.orm.shop;

import com.ysxsoft.common_base.base.BaseApplication;
import com.ysxsoft.common_base.utils.ToastUtils;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * create by Sincerly on 2019/6/29 0029
 **/
public class ShopCart extends DataSupport {
    public int id;
    public String productId;//商品id
    public int num;//当前购物车数量
    public String price;//商品单价
    public String ruleId;//有规格的ruleid
    public String ruleName;//有规格的ruleName
    public String cate1;//1级分类
    public String cate2;//2级分类
    public int isRuleProduct;//0是多规格(选规格) 1单规格(加减数量)
    public String productName;//商品名称
    public int cartType;//0超市  1美食

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductId() {
        return productId == null ? "" : productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getNum() {
        return num;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public int getCartType() {
        return cartType;
    }

    public void setCartType(int cartType) {
        this.cartType = cartType;
    }

    public String getCate1() {
        return cate1 == null ? "" : cate1;
    }

    public void setCate1(String cate1) {
        this.cate1 = cate1;
    }

    public String getCate2() {
        return cate2 == null ? "" : cate2;
    }

    public void setCate2(String cate2) {
        this.cate2 = cate2;
    }

    public int getIsRuleProduct() {
        return isRuleProduct;
    }

    public void setIsRuleProduct(int isRuleProduct) {
        this.isRuleProduct = isRuleProduct;
    }

    public String getProductName() {
        return productName == null ? "" : productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price == null ? "" : price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 数据库操作
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 清空(美食/商品)购物车
     *
     * @param cartType
     */
    public static void clearByType(int cartType) {
        ShopCart.deleteAll(ShopCart.class, "cartType=?", String.valueOf(cartType));
    }

    /**
     * 清空所有购物车 退出时候调用
     */
    public static void clearAll() {
        ShopCart.deleteAll(ShopCart.class);
    }

    /**
     * 添加(美食/商品)加入购物车
     *
     * @param cart     购物车对象
     * @param cartType
     */
    public static int addToCart(ShopCart cart, int cartType) {
        int updateCount = 0;//受影响行数
        ShopCart shopCart = DataSupport.where("productId=? and cartType=?", cart.getProductId(), String.valueOf(cartType)).findLast(ShopCart.class);
        if (shopCart == null) {
            //数据库里边没有这个购物车
            ShopCart item = new ShopCart();
            item.setProductId(cart.getProductId());//商品id
            item.setNum(1);//当前购物车数量
            item.setPrice(cart.getPrice());
            item.setRuleId(cart.getRuleId());//有规格的ruleid
            item.setRuleName(cart.getRuleName());//有规格的rulename
            item.setCate1(cart.getCate1());//1级分类
            item.setCate2(cart.getCate2());//2级分类
            item.setIsRuleProduct(cart.getIsRuleProduct());//0是多规格(选规格) 1单规格(加减数量)
            item.setProductName(cart.getProductName());//商品名称
            item.setCartType(cartType);//0超市  1美食
            item.save();
        } else {
            //数据库里边有这个购物车
            int num = shopCart.getNum();
            num++;
            shopCart.setNum(num);
            updateCount = shopCart.update(shopCart.getId());
        }
        return updateCount;
    }

    /**
     * 添加(美食/商品)加入购物车
     *
     * @param cart     购物车对象
     * @param cartType
     */
    public static int addToCart(ShopCart cart, int cartType, String ruleId) {
        int updateCount = 0;//受影响行数
        ShopCart shopCart = DataSupport.where("productId=? and cartType=? and ruleId=?", cart.getProductId(), String.valueOf(cartType), ruleId).findLast(ShopCart.class);
        if (shopCart == null) {
            //数据库里边没有这个购物车
            ShopCart item = new ShopCart();
            item.setProductId(cart.getProductId());//商品id
            item.setNum(1);//当前购物车数量
            item.setPrice(cart.getPrice());
            item.setRuleId(cart.getRuleId());//有规格的ruleid
            item.setRuleName(cart.getRuleName());//有规格的rulename
            item.setCate1(cart.getCate1());//1级分类
            item.setCate2(cart.getCate2());//2级分类
            item.setIsRuleProduct(cart.getIsRuleProduct());//0是多规格(选规格) 1单规格(加减数量)
            item.setProductName(cart.getProductName());//商品名称
            item.setCartType(cartType);//0超市  1美食
            item.save();
        } else {
            //数据库里边有这个购物车
            int num = shopCart.getNum();
            num++;
            shopCart.setNum(num);
            updateCount = shopCart.update(shopCart.getId());
        }
        return updateCount;
    }

    /**
     * 判断商品有没有在购物车
     *
     * @return
     */
    public static boolean checkShopCart(String productId) {
        boolean exisit = false;
        ShopCart shopCart = DataSupport.where("productId=? and cartType=?", productId, String.valueOf(0)).findLast(ShopCart.class);
        if (shopCart == null) {
            exisit = false;
        } else {
            exisit = true;
        }
        return exisit;
    }

    /**
     * 判断商品有没有在购物车
     *
     * @return
     */
    public static boolean checkShopCart(String productId, String cartType,String ruleId) {
        boolean exisit = false;
        ShopCart shopCart = DataSupport.where("productId=? and cartType=? and ruleId=?", productId, cartType, ruleId).findLast(ShopCart.class);
        if (shopCart == null) {
            exisit = false;
        } else {
            exisit = true;
        }
        return exisit;
    }


    /**
     * 判断美食商品有没有在购物车
     *
     * @return
     */
    public static boolean checkFoodCart(String productId) {
        boolean exisit = false;
        ShopCart shopCart = DataSupport.where("productId=? and cartType=?", productId, String.valueOf(1)).findLast(ShopCart.class);
        if (shopCart == null) {
            exisit = false;
        } else {
            exisit = true;
        }
        return exisit;
    }

    /**
     * 减少(美食/商品)购物车数量
     */
    public static int reduceCartNum(String productId, int cartType) {
        int updateCount = 0;
        ShopCart shopCart = DataSupport.where("productId=? and cartType=?", productId, String.valueOf(cartType)).findLast(ShopCart.class);
        if (shopCart == null) {
            ToastUtils.show(BaseApplication.getContext(), "请添加购物车！");
        } else {
            //数据库里边有这个购物车
            int num = shopCart.getNum();
            if (num == 1) {
                updateCount = shopCart.delete();//剩余最后一个删除
            } else {
                num--;
                shopCart.setNum(num);
                updateCount = shopCart.update(shopCart.getId());
            }
        }
        return updateCount;
    }

    /**
     * 减少(美食/商品)购物车数量
     */
    public static int reduceCartNum(String productId, int cartType, String ruleId) {
        int updateCount = 0;
        ShopCart shopCart = DataSupport.where("productId=? and cartType=? and ruleId=?", productId, String.valueOf(cartType), ruleId).findLast(ShopCart.class);
        if (shopCart == null) {
            ToastUtils.show(BaseApplication.getContext(), "请添加购物车！");
        } else {
            //数据库里边有这个购物车
            int num = shopCart.getNum();
            if (num == 1) {
                updateCount = shopCart.delete();//剩余最后一个删除
            } else {
                num--;
                shopCart.setNum(num);
                updateCount = shopCart.update(shopCart.getId());
            }
        }
        return updateCount;
    }

    /**
     * 获取(美食/商品)单个商品在购物车的数量
     * carType 0超市  1美食
     */
    public static int getCartNumByProductId(String productId, int cartType) {
        int num = 0;
        List<ShopCart> shopCart = DataSupport.where("productId=? and cartType=?", productId, String.valueOf(cartType)).find(ShopCart.class);
        if (shopCart == null) {
        } else {
            //数据库里边有这个购物车
            //num = shopCart.getNum();
            for (int i = 0; i < shopCart.size(); i++) {
                num += shopCart.get(i).getNum();
            }
        }
        return num;
    }

    /**
     * 判断(超市/美食)一级分类是否存在购物车
     * carType 0超市  1美食
     */
    public static boolean checkCartNumByCate1(int cartType, String cate1) {
        boolean exist = false;
        List<ShopCart> shopCart = DataSupport.where("cartType=? and cate1=?", String.valueOf(cartType), cate1).find(ShopCart.class);
        if (shopCart == null) {
        } else {
            if (shopCart.isEmpty()) {
                exist = false;
            } else {
                exist = true;
            }
        }
        return exist;
    }

    /**
     * 获取(超市/美食)二级分类购物车的数量
     * carType 0超市  1美食
     */
    public static int getCartNumByCate2(int cartType, String cate2) {
        int num = 0;
        List<ShopCart> shopCart = DataSupport.where("cartType=? and cate2=?", String.valueOf(cartType), cate2).find(ShopCart.class);
        if (shopCart == null) {
        } else {
            //数据库里边有这个购物车
            //num = shopCart.getNum();
            for (int i = 0; i < shopCart.size(); i++) {
                num += shopCart.get(i).getNum();
            }
        }
        return num;
    }


    /**
     * 获取(美食/商品)单个商品在购物车的数量
     * carType 0超市  1美食
     */
    public static int getCartNumByProductAndRuleId(String productId, int cartType, String ruleId) {
        int num = 0;
        List<ShopCart> shopCart = DataSupport.where("productId=? and cartType=? and ruleId=?", productId, String.valueOf(cartType), ruleId).find(ShopCart.class);
        if (shopCart == null) {
        } else {
            //数据库里边有这个购物车
            //num = shopCart.getNum();
            for (int i = 0; i < shopCart.size(); i++) {
                num += shopCart.get(i).getNum();
            }
        }
        return num;
    }

    /**
     * 获取(美食/商品)单个商品在购物车的规格id
     * carType 0超市  1美食
     */
    public static String getRuleIdByProductId(String productId, int cartType) {
        String ruleId = "";
        ShopCart shopCart = DataSupport.where("productId=? and cartType=?", productId, String.valueOf(cartType)).findLast(ShopCart.class);
        if (shopCart == null) {
        } else {
            //数据库里边有这个购物车
            ruleId = shopCart.getRuleId();
        }
        return ruleId;
    }

    /**
     * 获取单个商品在购物车中的总金额
     */
    public static double getCartMoney(String productId,int cartType) {
        double money = 0;
        List<ShopCart> shopCart = DataSupport.where("productId=? and cartType=?",productId, String.valueOf(cartType)).find(ShopCart.class);
        if (shopCart != null) {
            for (int i = 0; i < shopCart.size(); i++) {
                ShopCart item = shopCart.get(i);
                int num = item.getNum();
                String price = item.getPrice();
                if ("".equals(price) || price == null) {
                    continue;
                }
                double m = num * Double.valueOf(price);
                money += m;
            }
        } else {
        }
        return money;
    }

    /**
     * 获取美食/商品购物车总金额
     */
    public static double getTotalCartMoney(int cartType) {
        double money = 0;
        List<ShopCart> shopCart = DataSupport.where("cartType=?", String.valueOf(cartType)).find(ShopCart.class);
        if (shopCart != null) {
            for (int i = 0; i < shopCart.size(); i++) {
                ShopCart item = shopCart.get(i);
                int num = item.getNum();
                String price = item.getPrice();
                if ("".equals(price) || price == null) {
                    continue;
                }
                double m = num * Double.valueOf(price);
                money += m;
            }
        } else {
        }
        return money;
    }

    /**
     * 获取美食/商品购物车数量
     */
    public static int getTotalCartNum(int cartType) {
        int num = 0;
        List<ShopCart> shopCart = DataSupport.where("cartType=?", String.valueOf(cartType)).find(ShopCart.class);
        if (shopCart != null) {
            for (int i = 0; i < shopCart.size(); i++) {
                ShopCart item = shopCart.get(i);
                int n = item.getNum();
                num += n;
            }
        } else {
        }
        return num;
    }

    /**
     * 获取美食/商品购物车数量
     */
    public static ShopCart getShopCartByProductId(String productId,int cartType) {
        ShopCart shopCart = DataSupport.where("productId=? and cartType=?",productId,String.valueOf(cartType)).findLast(ShopCart.class);
        if (shopCart != null) {
        }else{
        }
        return shopCart;
    }

    /**
     * 获取美食/商品购物车数量
     */
    public static List<ShopCart> getCart(int cartType) {
        List<ShopCart> shopCart = DataSupport.where("cartType=?", String.valueOf(cartType)).find(ShopCart.class);
        if (shopCart != null) {
            Map<String, ShopCart> map = new HashMap<>();
            for (int i = 0; i < shopCart.size(); i++) {
                String productId = shopCart.get(i).getProductId();
                if (map.get(productId) == null) {
                    map.put(productId, shopCart.get(i));
                } else {
                    ShopCart cart = map.get(productId);
                    int n = cart.getNum() + shopCart.get(i).getNum();
                    cart.setNum(n);
                }
            }
            List<ShopCart> data = new ArrayList<>();
            Set<String> set = map.keySet();
            Iterator iterable = set.iterator();
            while (iterable.hasNext()) {
                String key = (String) iterable.next();
                data.add(map.get(key));
            }
            return data;
        } else {
            shopCart = new ArrayList<>();
        }
        return shopCart;
    }

    /**
     * 获取美食/商品购物车数量 (不分组)
     */
    public static List<ShopCart> getCartNoGroup(int cartType) {
        List<ShopCart> shopCart = DataSupport.where("cartType=?", String.valueOf(cartType)).find(ShopCart.class);
        if (shopCart != null) {
        } else {
            shopCart = new ArrayList<>();
        }
        return shopCart;
    }
}
