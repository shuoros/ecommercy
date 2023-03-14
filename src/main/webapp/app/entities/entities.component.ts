import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import ProductService from './product/product.service';
import PictureService from './picture/picture.service';
import GroupService from './group/group.service';
import CategoryService from './category/category.service';
import AttributeService from './attribute/attribute.service';
import ProductMainAttributeService from './product-main-attribute/product-main-attribute.service';
import ItemService from './item/item.service';
import OrderService from './order/order.service';
import CouponService from './coupon/coupon.service';
import BasketService from './basket/basket.service';
import CustomerService from './customer/customer.service';
import CommentService from './comment/comment.service';
import CityService from './city/city.service';
import StateService from './state/state.service';
import AddressService from './address/address.service';
import CountryService from './country/country.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('productService') private productService = () => new ProductService();
  @Provide('pictureService') private pictureService = () => new PictureService();
  @Provide('groupService') private groupService = () => new GroupService();
  @Provide('categoryService') private categoryService = () => new CategoryService();
  @Provide('attributeService') private attributeService = () => new AttributeService();
  @Provide('productMainAttributeService') private productMainAttributeService = () => new ProductMainAttributeService();
  @Provide('itemService') private itemService = () => new ItemService();
  @Provide('orderService') private orderService = () => new OrderService();
  @Provide('couponService') private couponService = () => new CouponService();
  @Provide('basketService') private basketService = () => new BasketService();
  @Provide('customerService') private customerService = () => new CustomerService();
  @Provide('commentService') private commentService = () => new CommentService();
  @Provide('cityService') private cityService = () => new CityService();
  @Provide('stateService') private stateService = () => new StateService();
  @Provide('addressService') private addressService = () => new AddressService();
  @Provide('countryService') private countryService = () => new CountryService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
