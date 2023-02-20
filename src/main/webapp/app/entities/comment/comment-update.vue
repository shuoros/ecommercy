<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="ecommercyApp.comment.home.createOrEditLabel"
          data-cy="CommentCreateUpdateHeading"
          v-text="$t('ecommercyApp.comment.home.createOrEditLabel')"
        >
          Create or edit a Comment
        </h2>
        <div>
          <div class="form-group" v-if="comment.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="comment.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.comment.text')" for="comment-text">Text</label>
            <input
              type="text"
              class="form-control"
              name="text"
              id="comment-text"
              data-cy="text"
              :class="{ valid: !$v.comment.text.$invalid, invalid: $v.comment.text.$invalid }"
              v-model="$v.comment.text.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.comment.product')" for="comment-product">Product</label>
            <select class="form-control" id="comment-product" data-cy="product" name="product" v-model="comment.product">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="comment.product && productOption.id === comment.product.id ? comment.product : productOption"
                v-for="productOption in products"
                :key="productOption.id"
              >
                {{ productOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.comment.customer')" for="comment-customer">Customer</label>
            <select class="form-control" id="comment-customer" data-cy="customer" name="customer" v-model="comment.customer">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="comment.customer && customerOption.id === comment.customer.id ? comment.customer : customerOption"
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
            :disabled="$v.comment.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./comment-update.component.ts"></script>
