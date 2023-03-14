<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="ecommercyApp.order.home.createOrEditLabel"
          data-cy="OrderCreateUpdateHeading"
          v-text="$t('ecommercyApp.order.home.createOrEditLabel')"
        >
          Create or edit a Order
        </h2>
        <div>
          <div class="form-group" v-if="order.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="order.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.order.status')" for="order-status">Status</label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !$v.order.status.$invalid, invalid: $v.order.status.$invalid }"
              v-model="$v.order.status.$model"
              id="order-status"
              data-cy="status"
            >
              <option
                v-for="orderStatus in orderStatusValues"
                :key="orderStatus"
                v-bind:value="orderStatus"
                v-bind:label="$t('ecommercyApp.OrderStatus.' + orderStatus)"
              >
                {{ orderStatus }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.order.receive')" for="order-receive">Receive</label>
            <div class="d-flex">
              <input
                id="order-receive"
                data-cy="receive"
                type="datetime-local"
                class="form-control"
                name="receive"
                :class="{ valid: !$v.order.receive.$invalid, invalid: $v.order.receive.$invalid }"
                :value="convertDateTimeFromServer($v.order.receive.$model)"
                @change="updateZonedDateTimeField('receive', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.order.address')" for="order-address">Address</label>
            <select class="form-control" id="order-address" data-cy="address" name="address" v-model="order.address">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="order.address && addressOption.id === order.address.id ? order.address : addressOption"
                v-for="addressOption in addresses"
                :key="addressOption.id"
              >
                {{ addressOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.order.coupon')" for="order-coupon">Coupon</label>
            <select class="form-control" id="order-coupon" data-cy="coupon" name="coupon" v-model="order.coupon">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="order.coupon && couponOption.id === order.coupon.id ? order.coupon : couponOption"
                v-for="couponOption in coupons"
                :key="couponOption.id"
              >
                {{ couponOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.order.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./order-update.component.ts"></script>
