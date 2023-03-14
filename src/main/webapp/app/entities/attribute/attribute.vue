<template>
  <div>
    <h2 id="page-heading" data-cy="AttributeHeading">
      <span v-text="$t('ecommercyApp.attribute.home.title')" id="attribute-heading">Attributes</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('ecommercyApp.attribute.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'AttributeCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-attribute"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('ecommercyApp.attribute.home.createLabel')"> Create a new Attribute </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && attributes && attributes.length === 0">
      <span v-text="$t('ecommercyApp.attribute.home.notFound')">No attributes found</span>
    </div>
    <div class="table-responsive" v-if="attributes && attributes.length > 0">
      <table class="table table-striped" aria-describedby="attributes">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('ecommercyApp.attribute.key')">Key</span></th>
            <th scope="row"><span v-text="$t('ecommercyApp.attribute.value')">Value</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="attribute in attributes" :key="attribute.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AttributeView', params: { attributeId: attribute.id } }">{{ attribute.id }}</router-link>
            </td>
            <td>{{ attribute.key }}</td>
            <td>{{ attribute.value }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'AttributeView', params: { attributeId: attribute.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'AttributeEdit', params: { attributeId: attribute.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(attribute)"
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
        ><span id="ecommercyApp.attribute.delete.question" data-cy="attributeDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-attribute-heading" v-text="$t('ecommercyApp.attribute.delete.question', { id: removeId })">
          Are you sure you want to delete this Attribute?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-attribute"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeAttribute()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./attribute.component.ts"></script>
