<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="ecommercyApp.item.home.createOrEditLabel"
          data-cy="ItemCreateUpdateHeading"
          v-text="$t('ecommercyApp.item.home.createOrEditLabel')"
        >
          Create or edit a Item
        </h2>
        <div>
          <div class="form-group" v-if="item.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="item.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.item.quantity')" for="item-quantity">Quantity</label>
            <input
              type="number"
              class="form-control"
              name="quantity"
              id="item-quantity"
              data-cy="quantity"
              :class="{ valid: !$v.item.quantity.$invalid, invalid: $v.item.quantity.$invalid }"
              v-model.number="$v.item.quantity.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.item.price')" for="item-price">Price</label>
            <input
              type="number"
              class="form-control"
              name="price"
              id="item-price"
              data-cy="price"
              :class="{ valid: !$v.item.price.$invalid, invalid: $v.item.price.$invalid }"
              v-model.number="$v.item.price.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.item.product')" for="item-product">Product</label>
            <select class="form-control" id="item-product" data-cy="product" name="product" v-model="item.product">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="item.product && productOption.id === item.product.id ? item.product : productOption"
                v-for="productOption in products"
                :key="productOption.id"
              >
                {{ productOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.item.basket')" for="item-basket">Basket</label>
            <select class="form-control" id="item-basket" data-cy="basket" name="basket" v-model="item.basket">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="item.basket && basketOption.id === item.basket.id ? item.basket : basketOption"
                v-for="basketOption in baskets"
                :key="basketOption.id"
              >
                {{ basketOption.id }}
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
            :disabled="$v.item.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./item-update.component.ts"></script>
