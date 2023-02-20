<template>
  <div>
    <h2 id="page-heading" data-cy="ProductMainAttributeHeading">
      <span v-text="$t('ecommercyApp.productMainAttribute.home.title')" id="product-main-attribute-heading">Product Main Attributes</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('ecommercyApp.productMainAttribute.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ProductMainAttributeCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-product-main-attribute"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('ecommercyApp.productMainAttribute.home.createLabel')"> Create a new Product Main Attribute </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && productMainAttributes && productMainAttributes.length === 0">
      <span v-text="$t('ecommercyApp.productMainAttribute.home.notFound')">No productMainAttributes found</span>
    </div>
    <div class="table-responsive" v-if="productMainAttributes && productMainAttributes.length > 0">
      <table class="table table-striped" aria-describedby="productMainAttributes">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('ecommercyApp.productMainAttribute.price')">Price</span></th>
            <th scope="row"><span v-text="$t('ecommercyApp.productMainAttribute.discount')">Discount</span></th>
            <th scope="row"><span v-text="$t('ecommercyApp.productMainAttribute.attribute')">Attribute</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="productMainAttribute in productMainAttributes" :key="productMainAttribute.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ProductMainAttributeView', params: { productMainAttributeId: productMainAttribute.id } }">{{
                productMainAttribute.id
              }}</router-link>
            </td>
            <td>{{ productMainAttribute.price }}</td>
            <td>{{ productMainAttribute.discount }}</td>
            <td>
              <div v-if="productMainAttribute.attribute">
                <router-link :to="{ name: 'AttributeView', params: { attributeId: productMainAttribute.attribute.id } }">{{
                  productMainAttribute.attribute.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'ProductMainAttributeView', params: { productMainAttributeId: productMainAttribute.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'ProductMainAttributeEdit', params: { productMainAttributeId: productMainAttribute.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(productMainAttribute)"
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
        ><span
          id="ecommercyApp.productMainAttribute.delete.question"
          data-cy="productMainAttributeDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-productMainAttribute-heading" v-text="$t('ecommercyApp.productMainAttribute.delete.question', { id: removeId })">
          Are you sure you want to delete this Product Main Attribute?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-productMainAttribute"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeProductMainAttribute()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./product-main-attribute.component.ts"></script>
