<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="order">
        <h2 class="jh-entity-heading" data-cy="orderDetailsHeading">
          <span v-text="$t('ecommercyApp.order.detail.title')">Order</span> {{ order.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('ecommercyApp.order.status')">Status</span>
          </dt>
          <dd>
            <span v-text="$t('ecommercyApp.OrderStatus.' + order.status)">{{ order.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.order.receive')">Receive</span>
          </dt>
          <dd>
            <span v-if="order.receive">{{ $d(Date.parse(order.receive), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.order.address')">Address</span>
          </dt>
          <dd>
            <div v-if="order.address">
              <router-link :to="{ name: 'AddressView', params: { addressId: order.address.id } }">{{ order.address.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.order.coupon')">Coupon</span>
          </dt>
          <dd>
            <div v-if="order.coupon">
              <router-link :to="{ name: 'CouponView', params: { couponId: order.coupon.id } }">{{ order.coupon.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="order.id" :to="{ name: 'OrderEdit', params: { orderId: order.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./order-details.component.ts"></script>
