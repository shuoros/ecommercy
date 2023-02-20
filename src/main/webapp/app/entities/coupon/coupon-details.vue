<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="coupon">
        <h2 class="jh-entity-heading" data-cy="couponDetailsHeading">
          <span v-text="$t('ecommercyApp.coupon.detail.title')">Coupon</span> {{ coupon.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('ecommercyApp.coupon.code')">Code</span>
          </dt>
          <dd>
            <span>{{ coupon.code }}</span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.coupon.type')">Type</span>
          </dt>
          <dd>
            <span v-text="$t('ecommercyApp.CouponType.' + coupon.type)">{{ coupon.type }}</span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.coupon.expiration')">Expiration</span>
          </dt>
          <dd>
            <span v-if="coupon.expiration">{{ $d(Date.parse(coupon.expiration), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('ecommercyApp.coupon.customer')">Customer</span>
          </dt>
          <dd>
            <span v-for="(customer, i) in coupon.customers" :key="customer.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'CustomerView', params: { customerId: customer.id } }">{{ customer.id }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="coupon.id" :to="{ name: 'CouponEdit', params: { couponId: coupon.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./coupon-details.component.ts"></script>
