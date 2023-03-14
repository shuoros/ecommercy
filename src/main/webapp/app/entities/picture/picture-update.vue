<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="ecommercyApp.picture.home.createOrEditLabel"
          data-cy="PictureCreateUpdateHeading"
          v-text="$t('ecommercyApp.picture.home.createOrEditLabel')"
        >
          Create or edit a Picture
        </h2>
        <div>
          <div class="form-group" v-if="picture.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="picture.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.picture.path')" for="picture-path">Path</label>
            <input
              type="text"
              class="form-control"
              name="path"
              id="picture-path"
              data-cy="path"
              :class="{ valid: !$v.picture.path.$invalid, invalid: $v.picture.path.$invalid }"
              v-model="$v.picture.path.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.picture.product')" for="picture-product">Product</label>
            <select class="form-control" id="picture-product" data-cy="product" name="product" v-model="picture.product">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="picture.product && productOption.id === picture.product.id ? picture.product : productOption"
                v-for="productOption in products"
                :key="productOption.id"
              >
                {{ productOption.id }}
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
            :disabled="$v.picture.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./picture-update.component.ts"></script>
