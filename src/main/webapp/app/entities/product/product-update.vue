<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="ecommercyApp.product.home.createOrEditLabel"
          data-cy="ProductCreateUpdateHeading"
          v-text="$t('ecommercyApp.product.home.createOrEditLabel')"
        >
          Create or edit a Product
        </h2>
        <div>
          <div class="form-group" v-if="product.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="product.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.product.name')" for="product-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="product-name"
              data-cy="name"
              :class="{ valid: !$v.product.name.$invalid, invalid: $v.product.name.$invalid }"
              v-model="$v.product.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.product.brand')" for="product-brand">Brand</label>
            <input
              type="text"
              class="form-control"
              name="brand"
              id="product-brand"
              data-cy="brand"
              :class="{ valid: !$v.product.brand.$invalid, invalid: $v.product.brand.$invalid }"
              v-model="$v.product.brand.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.product.rate')" for="product-rate">Rate</label>
            <input
              type="number"
              class="form-control"
              name="rate"
              id="product-rate"
              data-cy="rate"
              :class="{ valid: !$v.product.rate.$invalid, invalid: $v.product.rate.$invalid }"
              v-model.number="$v.product.rate.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.product.description')" for="product-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="product-description"
              data-cy="description"
              :class="{ valid: !$v.product.description.$invalid, invalid: $v.product.description.$invalid }"
              v-model="$v.product.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.product.mainPic')" for="product-mainPic">Main Pic</label>
            <select class="form-control" id="product-mainPic" data-cy="mainPic" name="mainPic" v-model="product.mainPic">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="product.mainPic && pictureOption.id === product.mainPic.id ? product.mainPic : pictureOption"
                v-for="pictureOption in pictures"
                :key="pictureOption.id"
              >
                {{ pictureOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.product.productMainAttribute')" for="product-productMainAttribute"
              >Product Main Attribute</label
            >
            <select
              class="form-control"
              id="product-productMainAttribute"
              data-cy="productMainAttribute"
              name="productMainAttribute"
              v-model="product.productMainAttribute"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  product.productMainAttribute && productMainAttributeOption.id === product.productMainAttribute.id
                    ? product.productMainAttribute
                    : productMainAttributeOption
                "
                v-for="productMainAttributeOption in productMainAttributes"
                :key="productMainAttributeOption.id"
              >
                {{ productMainAttributeOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label v-text="$t('ecommercyApp.product.attribute')" for="product-attribute">Attribute</label>
            <select
              class="form-control"
              id="product-attributes"
              data-cy="attribute"
              multiple
              name="attribute"
              v-if="product.attributes !== undefined"
              v-model="product.attributes"
            >
              <option
                v-bind:value="getSelected(product.attributes, attributeOption)"
                v-for="attributeOption in attributes"
                :key="attributeOption.id"
              >
                {{ attributeOption.id }}
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
            :disabled="$v.product.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./product-update.component.ts"></script>
