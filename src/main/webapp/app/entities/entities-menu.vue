<template>
  <div>
    <b-dropdown-item to="/product">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.product')">Product</span>
    </b-dropdown-item>
    <b-dropdown-item to="/picture">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.picture')">Picture</span>
    </b-dropdown-item>
    <b-dropdown-item to="/group">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.group')">Group</span>
    </b-dropdown-item>
    <b-dropdown-item to="/category">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.category')">Category</span>
    </b-dropdown-item>
    <b-dropdown-item to="/attribute">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.attribute')">Attribute</span>
    </b-dropdown-item>
    <b-dropdown-item to="/product-main-attribute">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.productMainAttribute')">Product Main Attribute</span>
    </b-dropdown-item>
    <b-dropdown-item to="/item">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.item')">Item</span>
    </b-dropdown-item>
    <b-dropdown-item to="/order">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.order')">Order</span>
    </b-dropdown-item>
    <b-dropdown-item to="/coupon">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.coupon')">Coupon</span>
    </b-dropdown-item>
    <b-dropdown-item to="/basket">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.basket')">Basket</span>
    </b-dropdown-item>
    <b-dropdown-item to="/customer">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.customer')">Customer</span>
    </b-dropdown-item>
    <b-dropdown-item to="/comment">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.comment')">Comment</span>
    </b-dropdown-item>
    <b-dropdown-item to="/city">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.city')">City</span>
    </b-dropdown-item>
    <b-dropdown-item to="/state">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.state')">State</span>
    </b-dropdown-item>
    <b-dropdown-item to="/address">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.address')">Address</span>
    </b-dropdown-item>
    <b-dropdown-item to="/country">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.country')">Country</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
