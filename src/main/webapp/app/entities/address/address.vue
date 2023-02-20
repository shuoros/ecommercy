<template>
  <div>
    <h2 id="page-heading" data-cy="AddressHeading">
      <span v-text="$t('ecommercyApp.address.home.title')" id="address-heading">Addresses</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('ecommercyApp.address.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'AddressCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-address"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('ecommercyApp.address.home.createLabel')"> Create a new Address </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && addresses && addresses.length === 0">
      <span v-text="$t('ecommercyApp.address.home.notFound')">No addresses found</span>
    </div>
    <div class="table-responsive" v-if="addresses && addresses.length > 0">
      <table class="table table-striped" aria-describedby="addresses">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('ecommercyApp.address.street1')">Street 1</span></th>
            <th scope="row"><span v-text="$t('ecommercyApp.address.street2')">Street 2</span></th>
            <th scope="row"><span v-text="$t('ecommercyApp.address.unit')">Unit</span></th>
            <th scope="row"><span v-text="$t('ecommercyApp.address.zipCode')">Zip Code</span></th>
            <th scope="row"><span v-text="$t('ecommercyApp.address.phoneNumber')">Phone Number</span></th>
            <th scope="row"><span v-text="$t('ecommercyApp.address.longitude')">Longitude</span></th>
            <th scope="row"><span v-text="$t('ecommercyApp.address.latitude')">Latitude</span></th>
            <th scope="row"><span v-text="$t('ecommercyApp.address.customer')">Customer</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="address in addresses" :key="address.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AddressView', params: { addressId: address.id } }">{{ address.id }}</router-link>
            </td>
            <td>{{ address.street1 }}</td>
            <td>{{ address.street2 }}</td>
            <td>{{ address.unit }}</td>
            <td>{{ address.zipCode }}</td>
            <td>{{ address.phoneNumber }}</td>
            <td>{{ address.longitude }}</td>
            <td>{{ address.latitude }}</td>
            <td>
              <div v-if="address.customer">
                <router-link :to="{ name: 'CustomerView', params: { customerId: address.customer.id } }">{{
                  address.customer.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'AddressView', params: { addressId: address.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'AddressEdit', params: { addressId: address.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(address)"
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
        ><span id="ecommercyApp.address.delete.question" data-cy="addressDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-address-heading" v-text="$t('ecommercyApp.address.delete.question', { id: removeId })">
          Are you sure you want to delete this Address?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-address"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeAddress()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./address.component.ts"></script>
