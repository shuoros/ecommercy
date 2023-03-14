<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="ecommercyApp.category.home.createOrEditLabel"
          data-cy="CategoryCreateUpdateHeading"
          v-text="$t('ecommercyApp.category.home.createOrEditLabel')"
        >
          Create or edit a Category
        </h2>
        <div>
          <div class="form-group" v-if="category.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="category.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.category.name')" for="category-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="category-name"
              data-cy="name"
              :class="{ valid: !$v.category.name.$invalid, invalid: $v.category.name.$invalid }"
              v-model="$v.category.name.$model"
            />
          </div>
          <div class="form-group">
            <label v-text="$t('ecommercyApp.category.product')" for="category-product">Product</label>
            <select
              class="form-control"
              id="category-products"
              data-cy="product"
              multiple
              name="product"
              v-if="category.products !== undefined"
              v-model="category.products"
            >
              <option
                v-bind:value="getSelected(category.products, productOption)"
                v-for="productOption in products"
                :key="productOption.id"
              >
                {{ productOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.category.group')" for="category-group">Group</label>
            <select class="form-control" id="category-group" data-cy="group" name="group" v-model="category.group">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="category.group && groupOption.id === category.group.id ? category.group : groupOption"
                v-for="groupOption in groups"
                :key="groupOption.id"
              >
                {{ groupOption.id }}
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
            :disabled="$v.category.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./category-update.component.ts"></script>
