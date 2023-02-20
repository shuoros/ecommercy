<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="item">
        <h2 class="jh-entity-heading" data-cy="itemDetailsHeading">
          <span v-text="$t('ecommercyApp.item.detail.title')">Item</span> {{ item.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('ecommercyApp.item.quantity')">Quantity</span>
          </dt>
          <dd>
            <span>{{ item.quantity }}</span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.item.price')">Price</span>
          </dt>
          <dd>
            <span>{{ item.price }}</span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.item.product')">Product</span>
          </dt>
          <dd>
            <div v-if="item.product">
              <router-link :to="{ name: 'ProductView', params: { productId: item.product.id } }">{{ item.product.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.item.basket')">Basket</span>
          </dt>
          <dd>
            <div v-if="item.basket">
              <router-link :to="{ name: 'BasketView', params: { basketId: item.basket.id } }">{{ item.basket.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="item.id" :to="{ name: 'ItemEdit', params: { itemId: item.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./item-details.component.ts"></script>
