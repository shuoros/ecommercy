<template>
  <div>
    <h2 id="page-heading" data-cy="CouponHeading">
      <span v-text="$t('ecommercyApp.coupon.home.title')" id="coupon-heading">Coupons</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('ecommercyApp.coupon.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'CouponCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-coupon"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('ecommercyApp.coupon.home.createLabel')"> Create a new Coupon </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && coupons && coupons.length === 0">
      <span v-text="$t('ecommercyApp.coupon.home.notFound')">No coupons found</span>
    </div>
    <div class="table-responsive" v-if="coupons && coupons.length > 0">
      <table class="table table-striped" aria-describedby="coupons">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('ecommercyApp.coupon.code')">Code</span></th>
            <th scope="row"><span v-text="$t('ecommercyApp.coupon.type')">Type</span></th>
            <th scope="row"><span v-text="$t('ecommercyApp.coupon.expiration')">Expiration</span></th>
            <th scope="row"><span v-text="$t('ecommercyApp.coupon.customer')">Customer</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="coupon in coupons" :key="coupon.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CouponView', params: { couponId: coupon.id } }">{{ coupon.id }}</router-link>
            </td>
            <td>{{ coupon.code }}</td>
            <td v-text="$t('ecommercyApp.CouponType.' + coupon.type)">{{ coupon.type }}</td>
            <td>{{ coupon.expiration ? $d(Date.parse(coupon.expiration), 'short') : '' }}</td>
            <td>
              <span v-for="(customer, i) in coupon.customers" :key="customer.id"
                >{{ i > 0 ? ', ' : '' }}
                <router-link class="form-control-static" :to="{ name: 'CustomerView', params: { customerId: customer.id } }">{{
                  customer.id
                }}</router-link>
              </span>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CouponView', params: { couponId: coupon.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CouponEdit', params: { couponId: coupon.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(coupon)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="ecommercyApp.coupon.delete.question" data-cy="couponDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-coupon-heading" v-text="$t('ecommercyApp.coupon.delete.question', { id: removeId })">
          Are you sure you want to delete this Coupon?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-coupon"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeCoupon()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./coupon.component.ts"></script>
