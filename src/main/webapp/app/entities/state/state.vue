<template>
  <div>
    <h2 id="page-heading" data-cy="StateHeading">
      <span v-text="$t('ecommercyApp.state.home.title')" id="state-heading">States</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('ecommercyApp.state.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'StateCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-state"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('ecommercyApp.state.home.createLabel')"> Create a new State </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && states && states.length === 0">
      <span v-text="$t('ecommercyApp.state.home.notFound')">No states found</span>
    </div>
    <div class="table-responsive" v-if="states && states.length > 0">
      <table class="table table-striped" aria-describedby="states">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('ecommercyApp.state.name')">Name</span></th>
            <th scope="row"><span v-text="$t('ecommercyApp.state.country')">Country</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="state in states" :key="state.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'StateView', params: { stateId: state.id } }">{{ state.id }}</router-link>
            </td>
            <td>{{ state.name }}</td>
            <td>
              <div v-if="state.country">
                <router-link :to="{ name: 'CountryView', params: { countryId: state.country.id } }">{{ state.country.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'StateView', params: { stateId: state.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'StateEdit', params: { stateId: state.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(state)"
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
        ><span id="ecommercyApp.state.delete.question" data-cy="stateDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-state-heading" v-text="$t('ecommercyApp.state.delete.question', { id: removeId })">
          Are you sure you want to delete this State?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-state"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeState()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./state.component.ts"></script>
