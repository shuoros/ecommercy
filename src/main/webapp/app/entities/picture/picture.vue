<template>
  <div>
    <h2 id="page-heading" data-cy="PictureHeading">
      <span v-text="$t('ecommercyApp.picture.home.title')" id="picture-heading">Pictures</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('ecommercyApp.picture.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PictureCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-picture"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('ecommercyApp.picture.home.createLabel')"> Create a new Picture </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && pictures && pictures.length === 0">
      <span v-text="$t('ecommercyApp.picture.home.notFound')">No pictures found</span>
    </div>
    <div class="table-responsive" v-if="pictures && pictures.length > 0">
      <table class="table table-striped" aria-describedby="pictures">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('ecommercyApp.picture.path')">Path</span></th>
            <th scope="row"><span v-text="$t('ecommercyApp.picture.product')">Product</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="picture in pictures" :key="picture.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PictureView', params: { pictureId: picture.id } }">{{ picture.id }}</router-link>
            </td>
            <td>{{ picture.path }}</td>
            <td>
              <div v-if="picture.product">
                <router-link :to="{ name: 'ProductView', params: { productId: picture.product.id } }">{{ picture.product.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PictureView', params: { pictureId: picture.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PictureEdit', params: { pictureId: picture.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(picture)"
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
        ><span id="ecommercyApp.picture.delete.question" data-cy="pictureDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-picture-heading" v-text="$t('ecommercyApp.picture.delete.question', { id: removeId })">
          Are you sure you want to delete this Picture?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-picture"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePicture()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./picture.component.ts"></script>
