<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="ecommercyApp.productMainAttribute.home.createOrEditLabel"
          data-cy="ProductMainAttributeCreateUpdateHeading"
          v-text="$t('ecommercyApp.productMainAttribute.home.createOrEditLabel')"
        >
          Create or edit a ProductMainAttribute
        </h2>
        <div>
          <div class="form-group" v-if="productMainAttribute.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="productMainAttribute.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.productMainAttribute.price')" for="product-main-attribute-price"
              >Price</label
            >
            <input
              type="number"
              class="form-control"
              name="price"
              id="product-main-attribute-price"
              data-cy="price"
              :class="{ valid: !$v.productMainAttribute.price.$invalid, invalid: $v.productMainAttribute.price.$invalid }"
              v-model.number="$v.productMainAttribute.price.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('ecommercyApp.productMainAttribute.discount')"
              for="product-main-attribute-discount"
              >Discount</label
            >
            <input
              type="number"
              class="form-control"
              name="discount"
              id="product-main-attribute-discount"
              data-cy="discount"
              :class="{ valid: !$v.productMainAttribute.discount.$invalid, invalid: $v.productMainAttribute.discount.$invalid }"
              v-model.number="$v.productMainAttribute.discount.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('ecommercyApp.productMainAttribute.attribute')"
              for="product-main-attribute-attribute"
              >Attribute</label
            >
            <select
              class="form-control"
              id="product-main-attribute-attribute"
              data-cy="attribute"
              name="attribute"
              v-model="productMainAttribute.attribute"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  productMainAttribute.attribute && attributeOption.id === productMainAttribute.attribute.id
                    ? productMainAttribute.attribute
                    : attributeOption
                "
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
            :disabled="$v.productMainAttribute.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./product-main-attribute-update.component.ts"></script>
