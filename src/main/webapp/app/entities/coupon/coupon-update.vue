<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="ecommercyApp.coupon.home.createOrEditLabel"
          data-cy="CouponCreateUpdateHeading"
          v-text="$t('ecommercyApp.coupon.home.createOrEditLabel')"
        >
          Create or edit a Coupon
        </h2>
        <div>
          <div class="form-group" v-if="coupon.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="coupon.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.coupon.code')" for="coupon-code">Code</label>
            <input
              type="text"
              class="form-control"
              name="code"
              id="coupon-code"
              data-cy="code"
              :class="{ valid: !$v.coupon.code.$invalid, invalid: $v.coupon.code.$invalid }"
              v-model="$v.coupon.code.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.coupon.type')" for="coupon-type">Type</label>
            <select
              class="form-control"
              name="type"
              :class="{ valid: !$v.coupon.type.$invalid, invalid: $v.coupon.type.$invalid }"
              v-model="$v.coupon.type.$model"
              id="coupon-type"
              data-cy="type"
            >
              <option
                v-for="couponType in couponTypeValues"
                :key="couponType"
                v-bind:value="couponType"
                v-bind:label="$t('ecommercyApp.CouponType.' + couponType)"
              >
                {{ couponType }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.coupon.expiration')" for="coupon-expiration">Expiration</label>
            <div class="d-flex">
              <input
                id="coupon-expiration"
                data-cy="expiration"
                type="datetime-local"
                class="form-control"
                name="expiration"
                :class="{ valid: !$v.coupon.expiration.$invalid, invalid: $v.coupon.expiration.$invalid }"
                :value="convertDateTimeFromServer($v.coupon.expiration.$model)"
                @change="updateZonedDateTimeField('expiration', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label v-text="$t('ecommercyApp.coupon.customer')" for="coupon-customer">Customer</label>
            <select
              class="form-control"
              id="coupon-customers"
              data-cy="customer"
              multiple
              name="customer"
              v-if="coupon.customers !== undefined"
              v-model="coupon.customers"
            >
              <option
                v-bind:value="getSelected(coupon.customers, customerOption)"
                v-for="customerOption in customers"
                :key="customerOption.id"
              >
                {{ customerOption.id }}
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
            :disabled="$v.coupon.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./coupon-update.component.ts"></script>
