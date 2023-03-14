<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="ecommercyApp.basket.home.createOrEditLabel"
          data-cy="BasketCreateUpdateHeading"
          v-text="$t('ecommercyApp.basket.home.createOrEditLabel')"
        >
          Create or edit a Basket
        </h2>
        <div>
          <div class="form-group" v-if="basket.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="basket.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.basket.score')" for="basket-score">Score</label>
            <input
              type="number"
              class="form-control"
              name="score"
              id="basket-score"
              data-cy="score"
              :class="{ valid: !$v.basket.score.$invalid, invalid: $v.basket.score.$invalid }"
              v-model.number="$v.basket.score.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.basket.price')" for="basket-price">Price</label>
            <input
              type="number"
              class="form-control"
              name="price"
              id="basket-price"
              data-cy="price"
              :class="{ valid: !$v.basket.price.$invalid, invalid: $v.basket.price.$invalid }"
              v-model.number="$v.basket.price.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.basket.order')" for="basket-order">Order</label>
            <select class="form-control" id="basket-order" data-cy="order" name="order" v-model="basket.order">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="basket.order && orderOption.id === basket.order.id ? basket.order : orderOption"
                v-for="orderOption in orders"
                :key="orderOption.id"
              >
                {{ orderOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.basket.customer')" for="basket-customer">Customer</label>
            <select class="form-control" id="basket-customer" data-cy="customer" name="customer" v-model="basket.customer">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="basket.customer && customerOption.id === basket.customer.id ? basket.customer : customerOption"
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
            :disabled="$v.basket.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./basket-update.component.ts"></script>
