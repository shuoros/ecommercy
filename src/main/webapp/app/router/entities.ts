import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const Product = () => import('@/entities/product/product.vue');
// prettier-ignore
const ProductUpdate = () => import('@/entities/product/product-update.vue');
// prettier-ignore
const ProductDetails = () => import('@/entities/product/product-details.vue');
// prettier-ignore
const Picture = () => import('@/entities/picture/picture.vue');
// prettier-ignore
const PictureUpdate = () => import('@/entities/picture/picture-update.vue');
// prettier-ignore
const PictureDetails = () => import('@/entities/picture/picture-details.vue');
// prettier-ignore
const Group = () => import('@/entities/group/group.vue');
// prettier-ignore
const GroupUpdate = () => import('@/entities/group/group-update.vue');
// prettier-ignore
const GroupDetails = () => import('@/entities/group/group-details.vue');
// prettier-ignore
const Category = () => import('@/entities/category/category.vue');
// prettier-ignore
const CategoryUpdate = () => import('@/entities/category/category-update.vue');
// prettier-ignore
const CategoryDetails = () => import('@/entities/category/category-details.vue');
// prettier-ignore
const Attribute = () => import('@/entities/attribute/attribute.vue');
// prettier-ignore
const AttributeUpdate = () => import('@/entities/attribute/attribute-update.vue');
// prettier-ignore
const AttributeDetails = () => import('@/entities/attribute/attribute-details.vue');
// prettier-ignore
const ProductMainAttribute = () => import('@/entities/product-main-attribute/product-main-attribute.vue');
// prettier-ignore
const ProductMainAttributeUpdate = () => import('@/entities/product-main-attribute/product-main-attribute-update.vue');
// prettier-ignore
const ProductMainAttributeDetails = () => import('@/entities/product-main-attribute/product-main-attribute-details.vue');
// prettier-ignore
const Item = () => import('@/entities/item/item.vue');
// prettier-ignore
const ItemUpdate = () => import('@/entities/item/item-update.vue');
// prettier-ignore
const ItemDetails = () => import('@/entities/item/item-details.vue');
// prettier-ignore
const Order = () => import('@/entities/order/order.vue');
// prettier-ignore
const OrderUpdate = () => import('@/entities/order/order-update.vue');
// prettier-ignore
const OrderDetails = () => import('@/entities/order/order-details.vue');
// prettier-ignore
const Coupon = () => import('@/entities/coupon/coupon.vue');
// prettier-ignore
const CouponUpdate = () => import('@/entities/coupon/coupon-update.vue');
// prettier-ignore
const CouponDetails = () => import('@/entities/coupon/coupon-details.vue');
// prettier-ignore
const Basket = () => import('@/entities/basket/basket.vue');
// prettier-ignore
const BasketUpdate = () => import('@/entities/basket/basket-update.vue');
// prettier-ignore
const BasketDetails = () => import('@/entities/basket/basket-details.vue');
// prettier-ignore
const Customer = () => import('@/entities/customer/customer.vue');
// prettier-ignore
const CustomerUpdate = () => import('@/entities/customer/customer-update.vue');
// prettier-ignore
const CustomerDetails = () => import('@/entities/customer/customer-details.vue');
// prettier-ignore
const Comment = () => import('@/entities/comment/comment.vue');
// prettier-ignore
const CommentUpdate = () => import('@/entities/comment/comment-update.vue');
// prettier-ignore
const CommentDetails = () => import('@/entities/comment/comment-details.vue');
// prettier-ignore
const City = () => import('@/entities/city/city.vue');
// prettier-ignore
const CityUpdate = () => import('@/entities/city/city-update.vue');
// prettier-ignore
const CityDetails = () => import('@/entities/city/city-details.vue');
// prettier-ignore
const State = () => import('@/entities/state/state.vue');
// prettier-ignore
const StateUpdate = () => import('@/entities/state/state-update.vue');
// prettier-ignore
const StateDetails = () => import('@/entities/state/state-details.vue');
// prettier-ignore
const Address = () => import('@/entities/address/address.vue');
// prettier-ignore
const AddressUpdate = () => import('@/entities/address/address-update.vue');
// prettier-ignore
const AddressDetails = () => import('@/entities/address/address-details.vue');
// prettier-ignore
const Country = () => import('@/entities/country/country.vue');
// prettier-ignore
const CountryUpdate = () => import('@/entities/country/country-update.vue');
// prettier-ignore
const CountryDetails = () => import('@/entities/country/country-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'product',
      name: 'Product',
      component: Product,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product/new',
      name: 'ProductCreate',
      component: ProductUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product/:productId/edit',
      name: 'ProductEdit',
      component: ProductUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product/:productId/view',
      name: 'ProductView',
      component: ProductDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'picture',
      name: 'Picture',
      component: Picture,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'picture/new',
      name: 'PictureCreate',
      component: PictureUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'picture/:pictureId/edit',
      name: 'PictureEdit',
      component: PictureUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'picture/:pictureId/view',
      name: 'PictureView',
      component: PictureDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'group',
      name: 'Group',
      component: Group,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'group/new',
      name: 'GroupCreate',
      component: GroupUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'group/:groupId/edit',
      name: 'GroupEdit',
      component: GroupUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'group/:groupId/view',
      name: 'GroupView',
      component: GroupDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'category',
      name: 'Category',
      component: Category,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'category/new',
      name: 'CategoryCreate',
      component: CategoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'category/:categoryId/edit',
      name: 'CategoryEdit',
      component: CategoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'category/:categoryId/view',
      name: 'CategoryView',
      component: CategoryDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'attribute',
      name: 'Attribute',
      component: Attribute,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'attribute/new',
      name: 'AttributeCreate',
      component: AttributeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'attribute/:attributeId/edit',
      name: 'AttributeEdit',
      component: AttributeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'attribute/:attributeId/view',
      name: 'AttributeView',
      component: AttributeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product-main-attribute',
      name: 'ProductMainAttribute',
      component: ProductMainAttribute,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product-main-attribute/new',
      name: 'ProductMainAttributeCreate',
      component: ProductMainAttributeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product-main-attribute/:productMainAttributeId/edit',
      name: 'ProductMainAttributeEdit',
      component: ProductMainAttributeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product-main-attribute/:productMainAttributeId/view',
      name: 'ProductMainAttributeView',
      component: ProductMainAttributeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'item',
      name: 'Item',
      component: Item,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'item/new',
      name: 'ItemCreate',
      component: ItemUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'item/:itemId/edit',
      name: 'ItemEdit',
      component: ItemUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'item/:itemId/view',
      name: 'ItemView',
      component: ItemDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'order',
      name: 'Order',
      component: Order,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'order/new',
      name: 'OrderCreate',
      component: OrderUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'order/:orderId/edit',
      name: 'OrderEdit',
      component: OrderUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'order/:orderId/view',
      name: 'OrderView',
      component: OrderDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'coupon',
      name: 'Coupon',
      component: Coupon,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'coupon/new',
      name: 'CouponCreate',
      component: CouponUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'coupon/:couponId/edit',
      name: 'CouponEdit',
      component: CouponUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'coupon/:couponId/view',
      name: 'CouponView',
      component: CouponDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'basket',
      name: 'Basket',
      component: Basket,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'basket/new',
      name: 'BasketCreate',
      component: BasketUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'basket/:basketId/edit',
      name: 'BasketEdit',
      component: BasketUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'basket/:basketId/view',
      name: 'BasketView',
      component: BasketDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer',
      name: 'Customer',
      component: Customer,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer/new',
      name: 'CustomerCreate',
      component: CustomerUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer/:customerId/edit',
      name: 'CustomerEdit',
      component: CustomerUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer/:customerId/view',
      name: 'CustomerView',
      component: CustomerDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'comment',
      name: 'Comment',
      component: Comment,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'comment/new',
      name: 'CommentCreate',
      component: CommentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'comment/:commentId/edit',
      name: 'CommentEdit',
      component: CommentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'comment/:commentId/view',
      name: 'CommentView',
      component: CommentDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'city',
      name: 'City',
      component: City,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'city/new',
      name: 'CityCreate',
      component: CityUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'city/:cityId/edit',
      name: 'CityEdit',
      component: CityUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'city/:cityId/view',
      name: 'CityView',
      component: CityDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'state',
      name: 'State',
      component: State,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'state/new',
      name: 'StateCreate',
      component: StateUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'state/:stateId/edit',
      name: 'StateEdit',
      component: StateUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'state/:stateId/view',
      name: 'StateView',
      component: StateDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'address',
      name: 'Address',
      component: Address,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'address/new',
      name: 'AddressCreate',
      component: AddressUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'address/:addressId/edit',
      name: 'AddressEdit',
      component: AddressUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'address/:addressId/view',
      name: 'AddressView',
      component: AddressDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country',
      name: 'Country',
      component: Country,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country/new',
      name: 'CountryCreate',
      component: CountryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country/:countryId/edit',
      name: 'CountryEdit',
      component: CountryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country/:countryId/view',
      name: 'CountryView',
      component: CountryDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
