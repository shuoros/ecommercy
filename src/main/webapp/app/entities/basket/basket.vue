<template>
  <div>
    <h2 id="page-heading" data-cy="BasketHeading">
      <span v-text="$t('ecommercyApp.basket.home.title')" id="basket-heading">Baskets</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('ecommercyApp.basket.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'BasketCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-basket"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('ecommercyApp.basket.home.createLabel')"> Create a new Basket </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && baskets && baskets.length === 0">
      <span v-text="$t('ecommercyApp.basket.home.notFound')">No baskets found</span>
    </div>
    <div class="table-responsive" v-if="baskets && baskets.length > 0">
      <table class="table table-striped" aria-describedby="baskets">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('ecommercyApp.basket.score')">Score</span></th>
            <th scope="row"><span v-text="$t('ecommercyApp.basket.price')">Price</span></th>
            <th scope="row"><span v-text="$t('ecommercyApp.basket.order')">Order</span></th>
            <th scope="row"><span v-text="$t('ecommercyApp.basket.customer')">Customer</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="basket in baskets" :key="basket.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'BasketView', params: { basketId: basket.id } }">{{ basket.id }}</router-link>
            </td>
            <td>{{ basket.score }}</td>
            <td>{{ basket.price }}</td>
            <td>
              <div v-if="basket.order">
                <router-link :to="{ name: 'OrderView', params: { orderId: basket.order.id } }">{{ basket.order.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="basket.customer">
                <router-link :to="{ name: 'CustomerView', params: { customerId: basket.customer.id } }">{{
                  basket.customer.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'BasketView', params: { basketId: basket.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'BasketEdit', params: { basketId: basket.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(basket)"
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
        ><span id="ecommercyApp.basket.delete.question" data-cy="basketDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-basket-heading" v-text="$t('ecommercyApp.basket.delete.question', { id: removeId })">
          Are you sure you want to delete this Basket?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-basket"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeBasket()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./basket.component.ts"></script>
